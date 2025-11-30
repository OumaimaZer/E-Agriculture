package estm.dut.eagri.controllers;

import com.google.gson.Gson;
import estm.dut.eagri.model.Esp32;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.CountDownLatch;

public class MqttSubscriber implements MqttCallback {

    private MqttClient client;
    private Gson gson = new Gson();
    private Esp32 esp32 = null;
    private CountDownLatch latch = new CountDownLatch(1);
    private String macAddress;

    public MqttSubscriber(String macAddress) throws MqttException {
        try {
            String brokerUrl = "tcp://broker.hivemq.com:1883";
            String topic = "esp32/estm";
            String clientId = MqttClient.generateClientId();
            MemoryPersistence persistence = new MemoryPersistence();
            client = new MqttClient(brokerUrl, clientId, persistence);
            client.setCallback(this);
            client.connect();
            client.subscribe(topic);

            this.macAddress = macAddress;
            publishMacAddress(topic);
        } catch (MqttException e) {
            // Handle the exception or rethrow it if necessary
            throw e;
        }
    }

    private void publishMacAddress(String topic) throws MqttException {
        MqttMessage message = new MqttMessage(macAddress.getBytes());
        client.publish(topic, message);
    }

    @Override
    public void connectionLost(Throwable cause) {
        // Handle connection lost
        System.out.println("Connection lost: " + cause.getMessage());
        try {
            client.reconnect();
        } catch (MqttException e) {
            System.out.println("Failed to reconnect: " + e.getMessage());
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            String payload = new String(message.getPayload(), "UTF-8");
            System.out.println("Received JSON payload: " + payload);
            esp32 = gson.fromJson(payload, Esp32.class);
            client.unsubscribe(topic);
            latch.countDown(); // count down the latch
        } catch (Exception e) {
            System.out.println("Failed to parse message: " + e.getMessage());
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Handle delivery complete
        System.out.println("Delivery complete: " + token.getMessageId());
    }

    public Esp32 receiveMessage() throws MqttException, InterruptedException {
        latch.await(); // wait until the latch is counted down
        return esp32;
    }
}