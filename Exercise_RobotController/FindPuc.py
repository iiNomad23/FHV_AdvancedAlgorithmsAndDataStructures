from Behavior import Behavior


class FindPuc(Behavior):
    def __init__(self):
        super().__init__()
        self.state = "finding puc"

    def is_applicable(self, robot):
        # only when nothing else is running
        # if puc found return false
        image = robot.getCameraImage()
        sensor_values = robot.getProximitySensorValues()
        print(sensor_values[0])
        if self.detect_box(image):
            robot.setMotorSpeeds(0, 0)
            return False
        elif sensor_values[0] < 0.04:
            return False
        else:
            return True

    def execute(self, robot):
        robot.setMotorSpeeds(0.5, 0)


