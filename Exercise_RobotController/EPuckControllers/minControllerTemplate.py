# -*- coding: utf-8 -*-
"""
minimal template for BasicEPuck.ePuckVRep
for usage with ePuckS5V12.ttm

@author: hoch ralph
"""
import time

from BasicEPuck.FollowingWall import FollowingWall
from BasicEPuck.ePuckVRep import EPuckVRep
from FindPuc import FindPuc
from Homing import Homing


def calculateMotorValues():
    """
    TODO: include parameters
    :return: (float,float)
        left and right motor velocity
    """
    # maximum velocity = ~2 Rad
    maxVel = 120 * 3.1415 / 180
    # TODO: calculate left and right motor velocity
    velRight = 1
    velLeft = 1

    return velLeft, velRight


def main():
    robot = EPuckVRep('ePuck', port=19999, synchronous=False)

    robot.enableCamera()
    robot.enableAllSensors()
    robot.setSensesAllTogether(False)  # we want fast sensing, so set robot to sensing mode where all sensors are sensed

    noDetectionDistance = 0.05 * robot.getS()  # maximum distance that proximity sensors of ePuck may sense
    find_puc = FindPuc()
    homing = Homing()
    following_wall = FollowingWall()
    behaviors = [find_puc, homing, following_wall]

    # main sense-act cycle
    while robot.isConnected():
        #print( 'proximity: ', robot.getProximitySensorValues())
        # print( 'ground: ', robot.getGroundSensorValues())
        # print( 'acceleration: ', robot.getAccelerometerValues())
        # print( 'wheel encoding: ', robot.getWheelEncoderValues())

        for behavior in behaviors:
            if behavior.is_applicable(robot):
                print(behavior.state)
                behavior.execute(robot)
                break

        acc = robot.getAccelerometerValues()[1]
        if (acc > 0.01):
            print('spike: ', acc)

        robot.fastSensingOverSignal()

        # sense
        distVector = robot.getProximitySensorValues()
        # print(distVector)

        # plan
        # leftMotor, rightMotor = calculateMotorValues()

        # act
        # robot.setMotorSpeeds(leftMotor, rightMotor)

        time.sleep(0.05)

    robot.disconnect()





if __name__ == '__main__':
    main()
