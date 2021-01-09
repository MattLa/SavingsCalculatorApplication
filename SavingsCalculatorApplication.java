package application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SavingsCalculatorApplication extends Application {

    private double interestRate = 0;
    private int savings = 25;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        
        BorderPane layout = new BorderPane();
        VBox selectionLayout = new VBox();
        selectionLayout.setSpacing(10);
        selectionLayout.setPadding(new Insets(20));
        BorderPane monthlySavings = new BorderPane();
        BorderPane yearlyInterest = new BorderPane();
        
        Label month = new Label("Monthly savings");
        Label year = new Label("Yearly interest rate");
        Label monthValue = new Label("25");
        Label yearValue = new Label("0");
        
        Slider savingsSlider = new Slider(25, 250, 25);
        savingsSlider.setPadding(new Insets(1));
        savingsSlider.setShowTickLabels(true);
        savingsSlider.setShowTickMarks(true);
        savingsSlider.setMajorTickUnit(25);
        savingsSlider.setBlockIncrement(1);
        
        Slider interestSlider = new Slider(0, 10, 0);
        interestSlider.setPadding(new Insets(1));
        interestSlider.setShowTickLabels(true);
        interestSlider.setShowTickMarks(true);
        interestSlider.setMajorTickUnit(5);
        interestSlider.setBlockIncrement(1);
        
        NumberAxis xAxis = new NumberAxis(0, 30, 1);
        NumberAxis yAxis = new NumberAxis(0, 27500, 2500);
        
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Savings calculator");
        
        layout.setTop(selectionLayout);
        layout.setCenter(lineChart);
        XYChart.Series monthData = new XYChart.Series<>();
        XYChart.Series interestData = new XYChart.Series<>();
        
        updateChart(lineChart, monthData, interestData);
        
        savingsSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                int value = newValue.intValue();
                monthValue.setText("" + value);
                savings = value;

                updateChart(lineChart, monthData, interestData);
            }
            
        });
        
        interestSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                
                double value = newValue.doubleValue();
                String precision = String.format("%.2f", value);
                value = Double.parseDouble(precision);
                yearValue.setText("" + value);
                interestRate = value / 100;
                
                updateChart(lineChart, monthData, interestData);
            }
            
        });
        
        monthlySavings.setLeft(month);
        monthlySavings.setCenter(savingsSlider);
        monthlySavings.setRight(monthValue);
        
        yearlyInterest.setLeft(year);
        yearlyInterest.setCenter(interestSlider);
        yearlyInterest.setRight(yearValue);
        
        selectionLayout.getChildren().add(monthlySavings);
        selectionLayout.getChildren().add(yearlyInterest);
        
        Scene view = new Scene(layout, 640, 400);
        stage.setScene(view);
        stage.show();
    }
    
    public void updateChart(LineChart<Number, Number> chart, XYChart.Series month, XYChart.Series interest) {
        if (month.getData().isEmpty()) {
            for (int i = 0; i <= 30; i++) {
                int total = (savings * 12) * i;
                month.getData().add(new XYChart.Data(i, total * (1 + interestRate)));
                interest.getData().add(new XYChart.Data(i, total * interestRate));
            }
            chart.getData().addAll(month, interest);
        } else {
            month.getData().stream().forEach(data -> {
                System.out.println(data);
            });
        }
        
    }
    
    

    public static void main(String[] args) {
        launch(SavingsCalculatorApplication.class);
    }

}
