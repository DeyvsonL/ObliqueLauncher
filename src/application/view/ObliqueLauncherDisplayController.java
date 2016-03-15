package application.view;

import data.Vector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import physics.ObliqueLauncherPhysics;

import java.io.IOException;

public class ObliqueLauncherDisplayController {

    //-- FXML properties --//

    @FXML
    private Slider massSlider;
    @FXML
    private TextField massField;

    @FXML
    private Slider gravitySlider;
    @FXML
    private TextField gravityField;

    @FXML
    private Slider launchAngleSlider;
    @FXML
    private TextField launchAngleField;

    @FXML
    private Slider launchVelocitySlider;
    @FXML
    private TextField launchVelocityField;

    @FXML
    private Slider airResistanceSlider;
    @FXML
    private TextField airResistanceField;

    @FXML
    private ComboBox<ObliqueLauncherPhysics.AirResistanceMode> airResistanceModeComboBox;

    // Charts
    @FXML
    private LineChart<Double, Double> positionChart;

    @FXML
    private LineChart<Double, Double> xPositionOverTimeChart;
    @FXML
    private LineChart<Double, Double> xVelocityOverTimeChart;
    @FXML
    private LineChart<Double, Double> xAccelerationOverTimeChart;

    //-- FXML Linked properties --//

    private XYChart.Series<Double, Double> positionAirResistanceSeries;
    private XYChart.Series<Double, Double> positionNonAirResistanceSeries;

    private XYChart.Series<Double, Double> xPositionOverTimeAirResistanceSeries;
    private XYChart.Series<Double, Double> xPositionOverTimeNonAirResistanceSeries;
    private XYChart.Series<Double, Double> xVelocityOverTimeAirResistanceSeries;
    private XYChart.Series<Double, Double> xVelocityOverTimeNonAirResistanceSeries;
    private XYChart.Series<Double, Double> xAccelerationOverTimeAirResistanceSeries;
    private XYChart.Series<Double, Double> xAccelerationOverTimeNonAirResistanceSeries;

    //-- Internal properties --//

    private volatile double mass;
    private volatile double gravity;
    private volatile double launchAngle;
    private volatile double launchVelocity;
    private volatile double airResistance;
    private volatile ObliqueLauncherPhysics.AirResistanceMode airResistanceMode;

    //-- Property constraints --//

    private static final double MIN_MASS = 0.1;
    private static final double MAX_MASS = 100;

    private static final double MIN_GRAVITY = -1;
    private static final double MAX_GRAVITY = -100;

    private static final double MIN_LAUNCH_ANGLE = 10;
    private static final double MAX_LAUNCH_ANGLE = 90;

    private static final double MIN_LAUNCH_VELOCITY = 10;
    private static final double MAX_LAUNCH_VELOCITY = 200;

    private static final double MIN_AIR_RESISTANCE = 0;
    private static final double MAX_AIR_RESISTANCE = 0.4;

    //-- Default property values --//

    private static final double DEFAULT_MASS = 10;
    private static final double DEFAULT_GRAVITY = -9.81;
    private static final double DEFAULT_LAUNCH_ANGLE = 75;
    private static final double DEFAULT_LAUNCH_VELOCITY = 45;
    private static final double DEFAULT_AIR_RESISTANCE = 0.005;

    //-- Methods --//

    @FXML
    private void initialize() {
        configureFXMLProperties();
        configureFXMLCharts();
        clearFXMLChartData();
    }

    private void configureFXMLProperties() {
        massSlider.setMin(MIN_MASS);
        massSlider.setMax(MAX_MASS);
        massSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            massField.setText(newValue.toString());
            mass = newValue.doubleValue();
        });
        massField.textProperty().addListener((observable, oldValue, newValue) -> {
            double value = Float.parseFloat(newValue);
            if (value < MIN_MASS) {
                value = MIN_MASS;
            } else if (value > MAX_MASS) {
                value = MAX_MASS;
            }
            massSlider.setValue(value);
            mass = value;
        });
        massSlider.setValue(DEFAULT_MASS);

        gravitySlider.setMin(MAX_GRAVITY);
        gravitySlider.setMax(MIN_GRAVITY);
        gravitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            gravityField.setText(newValue.toString());
            gravity = newValue.doubleValue();
        });
        gravityField.textProperty().addListener((observable, oldValue, newValue) -> {
            double value = Float.parseFloat(newValue);
            if (value < MAX_GRAVITY) {
                value = MAX_GRAVITY;
            } else if (value > MIN_GRAVITY) {
                value = MIN_GRAVITY;
            }
            gravitySlider.setValue(value);
            gravity = value;
        });
        gravitySlider.setValue(DEFAULT_GRAVITY);

        launchAngleSlider.setMin(MIN_LAUNCH_ANGLE);
        launchAngleSlider.setMax(MAX_LAUNCH_ANGLE);
        launchAngleSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            launchAngleField.setText(newValue.toString());
            launchAngle = newValue.doubleValue();
        });
        launchAngleField.textProperty().addListener((observable, oldValue, newValue) -> {
            double value = Float.parseFloat(newValue);
            if (value < MIN_LAUNCH_ANGLE) {
                value = MIN_LAUNCH_ANGLE;
            } else if (value > MAX_LAUNCH_ANGLE) {
                value = MAX_LAUNCH_ANGLE;
            }
            launchAngleSlider.setValue(value);
            launchAngle = value;
        });
        launchAngleSlider.setValue(DEFAULT_LAUNCH_ANGLE);

        launchVelocitySlider.setMin(MIN_LAUNCH_VELOCITY);
        launchVelocitySlider.setMax(MAX_LAUNCH_VELOCITY);
        launchVelocitySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            launchVelocityField.setText(newValue.toString());
            launchVelocity = newValue.doubleValue();
        });
        launchVelocityField.textProperty().addListener((observable, oldValue, newValue) -> {
            double value = Float.parseFloat(newValue);
            if (value < MIN_LAUNCH_VELOCITY) {
                value = MIN_LAUNCH_VELOCITY;
            } else if (value > MAX_LAUNCH_VELOCITY) {
                value = MAX_LAUNCH_VELOCITY;
            }
            launchVelocitySlider.setValue(value);
            launchVelocity = value;
        });
        launchVelocitySlider.setValue(DEFAULT_LAUNCH_VELOCITY);

        airResistanceSlider.setMin(MIN_AIR_RESISTANCE);
        airResistanceSlider.setMax(MAX_AIR_RESISTANCE);
        airResistanceSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            airResistanceField.setText(newValue.toString());
            airResistance = newValue.doubleValue();
        });
        airResistanceField.textProperty().addListener((observable, oldValue, newValue) -> {
            double value = Float.parseFloat(newValue);
            if (value < MIN_AIR_RESISTANCE) {
                value = MIN_AIR_RESISTANCE;
            } else if (value > MAX_AIR_RESISTANCE) {
                value = MAX_AIR_RESISTANCE;
            }
            airResistanceSlider.setValue(value);
            airResistance = value;
        });
        airResistanceSlider.setValue(DEFAULT_AIR_RESISTANCE);

        airResistanceModeComboBox.getItems()
                .addAll(ObliqueLauncherPhysics.AirResistanceMode.values());
        airResistanceModeComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            airResistanceMode = newValue;
        }));
        airResistanceModeComboBox.getSelectionModel()
                .select(ObliqueLauncherPhysics.AirResistanceMode.LINEAR);
    }

    private void configureFXMLCharts() {
        positionChart.setCreateSymbols(false);

        xPositionOverTimeChart.setCreateSymbols(false);
        xVelocityOverTimeChart.setCreateSymbols(false);
        xAccelerationOverTimeChart.setCreateSymbols(false);

        positionChart.getData().clear();

        xPositionOverTimeChart.getData().clear();
        xVelocityOverTimeChart.getData().clear();
        xAccelerationOverTimeChart.getData().clear();

        positionAirResistanceSeries = new XYChart.Series<>();
        positionNonAirResistanceSeries = new XYChart.Series<>();
        positionAirResistanceSeries.setName("Air resistance");
        positionNonAirResistanceSeries.setName("Non air resistance");
        positionChart.getData().addAll(positionAirResistanceSeries, positionNonAirResistanceSeries);


        xPositionOverTimeAirResistanceSeries = new XYChart.Series<>();
        xPositionOverTimeNonAirResistanceSeries = new XYChart.Series<>();
        xPositionOverTimeAirResistanceSeries.setName("Air resistance");
        xPositionOverTimeNonAirResistanceSeries.setName("Non air resistance");
        xPositionOverTimeChart.getData().addAll(
                xPositionOverTimeAirResistanceSeries, xPositionOverTimeNonAirResistanceSeries);

        xVelocityOverTimeAirResistanceSeries = new XYChart.Series<>();
        xVelocityOverTimeNonAirResistanceSeries = new XYChart.Series<>();
        xVelocityOverTimeAirResistanceSeries.setName("Air resistance");
        xVelocityOverTimeNonAirResistanceSeries.setName("Non air resistance");
        xVelocityOverTimeChart.getData().addAll(
                xVelocityOverTimeAirResistanceSeries, xVelocityOverTimeNonAirResistanceSeries);

        xAccelerationOverTimeAirResistanceSeries = new XYChart.Series<>();
        xAccelerationOverTimeNonAirResistanceSeries = new XYChart.Series<>();
        xAccelerationOverTimeAirResistanceSeries.setName("Air resistance");
        xAccelerationOverTimeNonAirResistanceSeries.setName("Non air resistance");
        xAccelerationOverTimeChart.getData().addAll(
                xAccelerationOverTimeAirResistanceSeries,
                xAccelerationOverTimeNonAirResistanceSeries);
    }

    private void clearFXMLChartData() {
        positionAirResistanceSeries.getData().clear();
        positionNonAirResistanceSeries.getData().clear();

        xPositionOverTimeAirResistanceSeries.getData().clear();
        xPositionOverTimeNonAirResistanceSeries.getData().clear();
        xVelocityOverTimeAirResistanceSeries.getData().clear();
        xVelocityOverTimeNonAirResistanceSeries.getData().clear();
        xAccelerationOverTimeAirResistanceSeries.getData().clear();
        xAccelerationOverTimeNonAirResistanceSeries.getData().clear();
    }

    private void calculateFXMLChartData() {
        ObliqueLauncherPhysics airResistancelauncher = new ObliqueLauncherPhysics(
                mass, Vector.dimVector(0, 0), Vector.magVector(launchVelocity, launchAngle),
                gravity, 0.01, airResistance, airResistanceMode);

        while (airResistancelauncher.getCurrentTime() == 0
                || airResistancelauncher.getCurrentPosition().getY() >= 0) {

            Vector position = airResistancelauncher.getCurrentPosition();
            Vector velocity = airResistancelauncher.getCurrentVelocity();
            Vector acceleration = airResistancelauncher.getCurrentAcceleration();
            double time = airResistancelauncher.getCurrentTime();

            positionAirResistanceSeries.getData()
                    .add(new XYChart.Data<>(position.getX(), position.getY()));

            xPositionOverTimeAirResistanceSeries.getData()
                    .add(new XYChart.Data<>(time, position.getX()));
            xVelocityOverTimeAirResistanceSeries.getData()
                    .add(new XYChart.Data<>(time, velocity.getX()));
            xAccelerationOverTimeAirResistanceSeries.getData()
                    .add(new XYChart.Data<>(time, acceleration.getX()));

            airResistancelauncher.calculateNextStep();
            System.out.println(position.getY());
        }
        
        ObliqueLauncherPhysics nonAirResistancelauncher = new ObliqueLauncherPhysics(
                mass, Vector.dimVector(0, 0), Vector.magVector(launchVelocity, launchAngle),
                gravity, 0.01, 0, airResistanceMode);

        while (nonAirResistancelauncher.getCurrentTime() == 0
                || nonAirResistancelauncher.getCurrentPosition().getY() >= 0) {

            Vector position = nonAirResistancelauncher.getCurrentPosition();
            Vector velocity = nonAirResistancelauncher.getCurrentVelocity();
            Vector acceleration = nonAirResistancelauncher.getCurrentAcceleration();
            double time = nonAirResistancelauncher.getCurrentTime();

            positionNonAirResistanceSeries.getData()
                    .add(new XYChart.Data<>(position.getX(), position.getY()));

            xPositionOverTimeNonAirResistanceSeries.getData()
                    .add(new XYChart.Data<>(time, position.getX()));
            xVelocityOverTimeNonAirResistanceSeries.getData()
                    .add(new XYChart.Data<>(time, velocity.getX()));
            xAccelerationOverTimeNonAirResistanceSeries.getData()
                    .add(new XYChart.Data<>(time, acceleration.getX()));

            nonAirResistancelauncher.calculateNextStep();
        }
    }

    //-- Button handles --//

    @FXML
    private void simulateButtonHandler() {
        clearFXMLChartData();
        calculateFXMLChartData();
    }

    //-- Loader --//

    public static ObliqueLauncherDisplayController load(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(
                ObliqueLauncherDisplayController.class.getResource("ObliqueLauncherDisplay.fxml"));
        ObliqueLauncherDisplayController controller = loader.getController();
        stage.setScene(new Scene(loader.load()));
        return controller;
    }
}
