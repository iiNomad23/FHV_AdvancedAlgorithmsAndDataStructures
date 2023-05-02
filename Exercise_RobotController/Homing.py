from Behavior import Behavior


class Homing(Behavior):
    def __init__(self):
        super().__init__()
        self.state = "homing"

    def is_applicable(self, robot):
        #return false when array 1 and 2 below 0.01
        image = robot.getCameraImage()
        sensor_values = robot.getProximitySensorValues()
        if not self.detect_box(image):
            return False
        elif sensor_values[2] < 0.035 or sensor_values[3] < 0.035:
            return False
        return True

    def execute(self, robot):
        robot.setMotorSpeeds(1, 1)