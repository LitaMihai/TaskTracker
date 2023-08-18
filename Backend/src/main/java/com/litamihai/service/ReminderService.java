package com.litamihai.service;

import com.litamihai.model.AcceptNotificationsModel;
import com.litamihai.model.TaskModel;
import com.litamihai.repository.ReminderRepository;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ReminderService {
    private final ReminderRepository reminderRepository;
    private PushService pushService;
    private AcceptNotificationsModel subscription;
    private boolean isSubscribed = false;
    private Subscription martinSubscription;
    private TaskService taskService;

    private String[] months = {
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
    };

    @Autowired
    public ReminderService(ReminderRepository reminderRepository, TaskService taskService) throws JoseException, GeneralSecurityException, IOException, ExecutionException, InterruptedException {
        this.reminderRepository = reminderRepository;
        this.taskService = taskService;
        this.initWebPush();
    }

    public AcceptNotificationsModel acceptNotifications(AcceptNotificationsModel model) {
        if(!reminderRepository.findAll().isEmpty())
            return model;
        return reminderRepository.save(model);
    }

    public List<AcceptNotificationsModel> getSubscription() {
        return reminderRepository.findAll();
    }

    public void initWebPush() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        if(this.pushService == null)
            this.pushService = new PushService();

        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }

        this.pushService.setPrivateKey("nWpUabe94Hf6BAcWizrcloJkJkMUG04R7yu8kPocf6E");
        this.pushService.setPublicKey("BEb0hUIa52hs-GqrSGbEonbk_PWOruKc9C_I9uTg0eEue4uirbbZBsYdZcP5kcjnlfRQfH9Hkc8-bfMOQksjRMw");

        if(this.subscription == null)
            if(!this.getSubscription().isEmpty()){
                this.subscription = this.getSubscription().get(0);
                this.isSubscribed = true;
            }

        martinSubscription = new Subscription();
        martinSubscription.keys = new Subscription.Keys();
        martinSubscription.endpoint = this.subscription.getEndpoint();
        martinSubscription.keys.auth = this.subscription.getKeys().getAuth();
        martinSubscription.keys.p256dh = this.subscription.getKeys().getP256dh();
    }

    public void notifyClient(String title, String body) throws GeneralSecurityException, JoseException, IOException, ExecutionException, InterruptedException {
        var json = """
                {"notification":{
                    "title": "%s",
                    "body": "%s"
                }}
                """;
        Notification notification = new Notification(
                martinSubscription, String.format(json, title, body)
        );

        pushService.send(notification);
    }

    @Scheduled(fixedRate = 10000)
    private void sendNotifications() throws JoseException, GeneralSecurityException, IOException, ExecutionException, InterruptedException {
        // If not subscribed.
        if(this.isSubscribed()) {
            try {
                this.initWebPush();
            } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchProviderException e) {
                throw new RuntimeException(e);
            }
        }

        // Get local time
        LocalDateTime localDateTime = LocalDateTime.now();
        int localHour = localDateTime.getHour();
        int localMinute = localDateTime.getMinute();
        int localYear = localDateTime.getYear();
        int localMonth = localDateTime.getMonthValue();
        int localDayOfMonth = localDateTime.getDayOfMonth();

        checkDueTasks(localHour, localMinute, localDayOfMonth, localMonth, localYear);
    }

    void checkDueTasks(int hour, int minute, int dayOfMonth, int month, int year) throws JoseException, GeneralSecurityException, IOException, ExecutionException, InterruptedException {
        // Check every task
        List<TaskModel> tasks = taskService.getAllTasks();

        for(TaskModel task : tasks){
            if(task.isReminder()){
                // Get the date and hour from the task
                String taskDate = task.getDate();

                // Get the hour and minute from the task
                String taskDateAndHour = taskDate.substring(taskDate.lastIndexOf("at") + 3);
                String[] segments = taskDateAndHour.split(":");
                int taskHour = Integer.parseInt(segments[0]);
                int taskMinute = Integer.parseInt(segments[1]);

                // Get the dayOfMonth, month and year from the task
                segments = taskDate.split(", ");

                // Now in segments[1] we have: "7 Aug 2023 at 05:28" (example)
                // We need to split it again
                String[] segments2 = segments[1].split(" ");
                int taskDayOfMonth = Integer.parseInt(segments2[0]); // 7
                int taskYear = Integer.parseInt(segments2[2]); // 2023

                // Get number of month
                int taskMonth = 0; // Aug
                for(int i = 0; i<months.length; i++)
                    if(months[i].compareTo(segments2[1]) == 0)
                        taskMonth = i + 1; // 8 = August

                // Check if the task is due
                if(
                        taskYear == year &&
                        taskMonth == month &&
                        taskDayOfMonth == dayOfMonth &&
                        taskHour == hour &&
                        taskMinute == minute
                ){
                    // It is due, so we notify and set the reminder to false
                    this.notifyClient(task.getText(), task.getDate());
                    task.setReminder(false);
                    taskService.updateTask(task);
                }
            }
        }
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }
}
