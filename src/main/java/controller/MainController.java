package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import rsa.RSA;
import rsa.RSAKeyPair;
import rsa.RSAPublicKey;

import java.math.BigInteger;

public class MainController {
    private class BitEntry extends Pair<Integer, String> {

        public BitEntry(Integer integer, String s) {
            super(integer, s);
        }

        @Override
        public String toString() {
            return super.getValue().toString();
        }
    }

    @FXML
    VBox mainVBox;
    @FXML
    ComboBox bitSelector;
    @FXML
    TextField publicE;
    @FXML
    TextField publicN;
    @FXML
    TextField privateD;
    @FXML
    TextField privateN;
    @FXML
    TextField numberToEncode;
    @FXML
    TextField textToEncode;
    @FXML
    TextField encodedNumber;
    @FXML
    TextField numberToDecode;
    @FXML
    TextField decodedNumber;
    @FXML
    TextField decodedText;
    @FXML
    Button exit;
    @FXML
    Button generate;
    @FXML
    Button encode;
    @FXML
    Button decode;

    @FXML
    public void initialize() {
        ObservableList<BitEntry> bitList = FXCollections.observableArrayList();
        BitEntry defaultEntry = new BitEntry(128, "128");
        bitList.add(defaultEntry);
        bitList.add(new BitEntry(256, "256"));
        bitList.add(new BitEntry(512, "512"));
        bitList.add(new BitEntry(1024, "1024"));
        bitSelector.setItems(bitList);
        bitSelector.getSelectionModel().select(defaultEntry);
    }

    public void disableAll(){
        bitSelector.setDisable(true);
        publicE.setDisable(true);
        publicN.setDisable(true);
        privateD.setDisable(true);
        privateN.setDisable(true);
        numberToEncode.setDisable(true);
        textToEncode.setDisable(true);
        encodedNumber.setDisable(true);
        numberToDecode.setDisable(true);
        decodedNumber.setDisable(true);
        decodedText.setDisable(true);
        generate.setDisable(true);
        encode.setDisable(true);
        decode.setDisable(true);
        exit.setDisable(true);
    }

    public void enableAll(){
        bitSelector.setDisable(false);
        publicE.setDisable(false);
        publicN.setDisable(false);
        privateD.setDisable(false);
        privateN.setDisable(false);
        numberToEncode.setDisable(false);
        textToEncode.setDisable(false);
        encodedNumber.setDisable(false);
        numberToDecode.setDisable(false);
        decodedNumber.setDisable(false);
        decodedText.setDisable(false);
        generate.setDisable(false);
        encode.setDisable(false);
        decode.setDisable(false);
        exit.setDisable(false);
    }    

    public void exit() {
        Stage stage = (Stage) mainVBox.getScene().getWindow();
        stage.close();
    }

    public void generate() {
        new Thread( () -> {
            publicE.setText("calculating...");
            publicN.setText("calculating...");
            privateD.setText("calculating...");
            privateN.setText("calculating...");
            disableAll();
            RSAKeyPair keyPair = RSA.generateKeys(((BitEntry) bitSelector.getSelectionModel().getSelectedItem()).getKey());
            publicE.setText(keyPair.getPublicKey().getE().toString());
            publicN.setText(keyPair.getPublicKey().getN().toString());
            privateD.setText(keyPair.getPrivateKey().getD().toString());
            privateN.setText(keyPair.getPrivateKey().getN().toString());
            enableAll();
            }).start();
    }

    public void encode() {
        new Thread( () -> {
            disableAll();
            encodedNumber.setText("calculating...");
            try {
                if (new BigInteger(numberToEncode.getText()).bitLength() > new BigInteger(publicN.getText()).bitLength()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Input number bit length is bigger than the bitlength of n");
                    alert.showAndWait();
                    return;
                }
                encodedNumber.setText(
                        RSA.encode(
                                new BigInteger(numberToEncode.getText()),
                                new RSAPublicKey(
                                        new BigInteger(publicE.getText()),
                                        new BigInteger(publicN.getText()))
                        ).toString());
            } catch (Exception e) {
                encodedNumber.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error during encryption.\nCheck the 'e', 'n' and 'number to encrypt' fields!");
                alert.showAndWait();
                return;
            }
            enableAll();
        }).start();
    }

    public void decode() {
        new Thread( () -> {
            disableAll();
            decodedNumber.setText("calculating...");
            decodedText.setText("calculating...");
            try {
                decodedNumber.setText(
                        RSA.encode(
                                new BigInteger(numberToDecode.getText()),
                                new RSAPublicKey(
                                        new BigInteger(privateD.getText()),
                                        new BigInteger(privateN.getText()))
                        ).toString());
                decodedText.setText(new String(new BigInteger(decodedNumber.getText()).toByteArray()));
            } catch (Exception e) {
                decodedNumber.setText("");
                decodedText.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error during decryption.\nCheck the 'd', 'n' and 'number to decrypt' fields!");
                alert.showAndWait();
                return;
            }
            enableAll();
        }).start();
    }

    public void textToEncodeType(){
        try {
            numberToEncode.setText(new BigInteger(textToEncode.getText().getBytes()).toString());
        } catch (Exception e){
            numberToEncode.setText("");
        }
    }


}
