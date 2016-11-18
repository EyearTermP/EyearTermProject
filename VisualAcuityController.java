package com.everychecking.controller;

import java.util.Random;

import com.everychecking.library.Callback;
import com.everychecking.model.check.CheckModel;
import com.everychecking.model.data.VisualAcuity;
import com.everychecking.session.Session;
import com.everychecking.voce.Listener;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

// Visual Acuity FXML 의 컨트롤러, 컨트롤러의 공통분모를 모은 CheckController 를 상속받는다.
public class VisualAcuityController extends CheckController {

   private CheckModel checkModel;
   
   private VisualAcuity visualAcuityObject = new VisualAcuity(0.0, 0.0);
   
   private int cursor = 0;
   private int currentVisualAcuity = 1;
   private boolean isCurrentEyeRight = false;
   
   private int wrongCount;
   
   private Text[] numbers = new Text[4];
   private JFXTextField[] inputs = new JFXTextField[4];
   private boolean[] tempIsDisables = new boolean[6];
   
   private Random random;
   
   private Thread currentThread;
   
   @FXML
   private Text prepareText;
   
   @FXML
   private Text firstNumber;
   @FXML
   private Text secondNumber;
   @FXML
   private Text thirdNumber;
   @FXML
   private Text fourthNumber;
   
   @FXML
   private JFXTextField firstInput;
   @FXML
   private JFXTextField secondInput;
   @FXML
   private JFXTextField thirdInput;
   @FXML
   private JFXTextField fourthInput;
   
   @FXML
   private Text visualAcuity;
   
   @FXML
   private JFXButton leftButton;
   @FXML
   private JFXButton rightButton;
   
   @FXML
   private JFXButton confirmButton;
   
   @FXML
   private JFXButton listenButton;
   
   @FXML
   private Text helpText;
   
   // FXML 의 초기화가 진행 된 후 호출되는 메서드이다.
   @FXML
   void initialize() {
      checkModel = Session.getCheckModel();
      
      prepareText.setText("준비가 되셨다면 검사를 진행해주세요.");
      helpText.setText(" ※ 컴퓨터 화면에서 80cm 떨어진 거리에서 오른쪽 눈을 가리고 검사에 응하시길 바랍니다.");
      
      visualAcuity.setText("" + ((double)currentVisualAcuity)/10);
      
      numbers[0] = firstNumber;
      numbers[1] = secondNumber;
      numbers[2] = thirdNumber;
      numbers[3] = fourthNumber;
      numbers[3].setFont(new Font(50));
      
      random = new Random();
      
      for (Text number : numbers) {
         number.setText("" + random.nextInt(6));
         number.setFont(new Font(125 - (currentVisualAcuity*6)));
      }
      
      inputs[0] = firstInput;
      inputs[1] = secondInput;
      inputs[2] = thirdInput;
      inputs[3] = fourthInput;
      
      for (JFXTextField input : inputs) {
         input.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
               if (event.getCode() == KeyCode.ENTER)
                  endCheck();
            }
         });
      }
      
      wrongCount = 0;
      
      initPane();
   }
   
   public void listenAction() {
      Listener.initailzing();

      prepareText.setText("잠시만 기다려주세요.");
      
      currentThread = Listener.listenAWord(new Callback() {
         @Override
         public void callback(Object object) {
            prepareText.setText((cursor+1) + " 번째 숫자를 말하세요.");
            focusAInputField(cursor);
         }
      }, new Callback() {
         @Override
         public void callback(Object object) {
            endCheck((String)object);
            listenButton.setDisable(false);
         }
      });
      
      listenButton.setDisable(true);
   }
   
   private void endCheck(String listenNumber) {
      String text = numbers[cursor].getText();
      
      int numberFromText = getNumberFromText(listenNumber);
      int numberFromText2 = Integer.parseInt(text);
      
      if (numberFromText == -1) {
         prepareText.setText(listenNumber + " 라고 말씀하신것같습니다. 잡음을 제거 후 정확한 발음으로 다시 시도해주세요.");
         return;
      }

      defocusInputFields();
      inputs[cursor].setText("" + numberFromText);
      inputs[cursor].setDisable(true);
      
      if (numberFromText != numberFromText2) {
         wrongCount++;
         
         if (wrongCount >= 2) {
            if (!isCurrentEyeRight) {
               visualAcuityObject.setLeft(((double)currentVisualAcuity)/10);
               isCurrentEyeRight = true;
               
               prepareText.setText("가리는 눈을 바꾸어주세요.");
               helpText.setText(" ※ 컴퓨터 화면에서 80cm 떨어진 거리에서 왼쪽 눈을 가리고 검사에 응하시길 바랍니다.");
               
               currentVisualAcuity = 1;
               
               initPane();
               return;
            } else {
               visualAcuityObject.setRight(((double)currentVisualAcuity)/10);
               checkModel.setVisualAcuity(visualAcuityObject);
               
               prepareText.setText("시력검사가 완료되었습니다. Confirm 버튼을 눌러주세요.");
               confirmButton.setDisable(false);
               leftButton.setDisable(true);
               rightButton.setDisable(true);
               listenButton.setDisable(true);
               if (currentThread != null) {
                  try {
                     currentThread.stop();
                  } catch (Exception e) {
                     e.printStackTrace();
                  }
               }
               return;
            }
         }
      }
      
      cursor++;
      
      if (cursor > 3) {
         cursor = 0;
         
         upVisualAcuity();
      }
      
      prepareText.setText("검사를 진행해주세요. (Wrong:" + wrongCount + ")");
   }
   
   private void endCheck() {
      JFXTextField jfxTextField = inputs[cursor];
      
      String text = jfxTextField.getText();
      String text2 = numbers[cursor].getText();
      
      if (text.isEmpty() || !text.matches("^[0-9]*$")) {
         prepareText.setText("입력된 값을 확인해주세요.");
         jfxTextField.setText("");
         jfxTextField.requestFocus();
         return;
      }
      
      jfxTextField.setDisable(true);
      
      if (!text.equals(text2)) {
         wrongCount++;
         if (wrongCount >= 2) {
            if (!isCurrentEyeRight) {
               visualAcuityObject.setLeft(((double)currentVisualAcuity)/10);
               isCurrentEyeRight = true;
               
               prepareText.setText("가리는 눈을 바꾸신 뒤 잠시만 기다려주세요.");
               helpText.setText(" ※ 컴퓨터 화면에서 80cm 떨어진 거리에서 왼쪽 눈을 가리고 검사에 응하시길 바랍니다.");
               
               currentVisualAcuity = 1;
               
               initPane();
               return;
            } else {
               visualAcuityObject.setRight(((double)currentVisualAcuity)/10);
               checkModel.setVisualAcuity(visualAcuityObject);
               
               prepareText.setText("시력검사가 완료되었습니다. Confirm 버튼을 눌러주세요.");
               confirmButton.setDisable(false);
               leftButton.setDisable(true);
               rightButton.setDisable(true);
               listenButton.setDisable(true);
               for (JFXTextField input : inputs) {
                  input.setDisable(true);
               }
               if (currentThread != null) {
                  try {
                     currentThread.stop();
                  } catch (Exception e) {
                     e.printStackTrace();
                  }
               }
               return;
            }
         }
      }
      
      cursor++;
      
      if (cursor > 3) {
         cursor = 0;
         
         upVisualAcuity();
      }
      
      inputs[cursor].setDisable(false);
      inputs[cursor].requestFocus();
      
      prepareText.setText("검사를 진행해주세요. (Wrong:" + wrongCount + ")");
   }
   
   public void confirmAction(){
      if (currentThread != null) {
         try {
            currentThread.stop();
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      
      confirmChecking();
   }
   
   public void helpAction () {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Audio Acuity");
      alert.setHeaderText("Help");
      alert.setContentText("안내에 따라 한쪽 눈을 가리고 화면에 보이는 숫자들을 말하여 입력하여 검사를 진행합니다.");
      alert.showAndWait();
   }
   
   public void upVisualAcuity() {
      if (currentVisualAcuity < 20)
         currentVisualAcuity += 1;
      
      if (currentVisualAcuity > 19)
         rightButton.setDisable(true);
      
      if (leftButton.isDisable())
         leftButton.setDisable(false);
      
      initPane();
   }
   
   public void downVisualAcuity() {
      if (currentVisualAcuity > 1)
         currentVisualAcuity -= 1;
      
      if (currentVisualAcuity < 2)
         leftButton.setDisable(true);
      
      if (rightButton.isDisable())
         rightButton.setDisable(false);
      
      initPane();
   }
   
   private void focusAInputField(int index) {
      for (int i = 0; i < inputs.length; i++) {
         tempIsDisables[i] = inputs[i].isDisable();
         inputs[i].setDisable(true);
      }
      
      tempIsDisables[4] = leftButton.isDisable();
      tempIsDisables[5] = rightButton.isDisable();
      
      leftButton.setDisable(true);
      rightButton.setDisable(true);
      
      inputs[index].setDisable(false);
   }
   
   private void defocusInputFields() {
      for (int i = 0; i < inputs.length; i++) {
         inputs[i].setDisable(tempIsDisables[i]);
      }

      leftButton.setDisable(tempIsDisables[4]);
      rightButton.setDisable(tempIsDisables[5]);
   }
   
   private void initPane() {
      wrongCount = 0;
      cursor = 0;
      
      visualAcuity.setText("" + ((double)currentVisualAcuity)/10);
      
      for (Text number : numbers) {
         number.setText("" + random.nextInt(6));
         number.setFont(new Font(125 - (currentVisualAcuity*6)));
      }
      
      for (int i = 0; i < inputs.length; i++) {
         inputs[i].setText("");
         inputs[i].setDisable(true);
      }

      if (currentVisualAcuity > 19)
         rightButton.setDisable(true);
      
      if (leftButton.isDisable())
         leftButton.setDisable(false);
      
      if (currentVisualAcuity < 2)
         leftButton.setDisable(true);
      
      if (rightButton.isDisable())
         rightButton.setDisable(false);
      
      listenButton.setDisable(false);

      inputs[0].setDisable(false);
      try {
         inputs[0].requestFocus();
      } catch (Exception e) {
      }
         
      if (currentThread != null) {
         try {
            currentThread.stop();
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }
   
   private int getNumberFromText(String text) {
      int result = -1;
      
      String lowerCase = text.toLowerCase();
      
      if (lowerCase.equals("zero")) {
         result = 0;
      } else if (lowerCase.equals("one")) {
         result = 1;
      } else if (lowerCase.equals("two")) {
         result = 2;
      } else if (lowerCase.equals("three")) {
         result = 3;
      } else if (lowerCase.equals("four")) {
         result = 4;
      } else if (lowerCase.equals("five")) {
         result = 5;
      }
      
      return result;
   }
}