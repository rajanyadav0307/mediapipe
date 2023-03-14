package com.google.mediapipe.examples.handlandmarker;

import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult;
import java.util.List;

public class HandGestureController {
    private static Integer HandsCount(List<HandLandmarkerResult> handLandmarks)
    {
        return handLandmarks.get(0).landmarks().size();
    }

    private static void Handness(List<HandLandmarkerResult> handLandmarks, int index)
    {
       System.out.println( handLandmarks.get(0).handednesses().get(0));
    }


    private static void IsHandOpen(List<HandLandmarkerResult> handLandmarks)
    {
        if(handLandmarks.get(0).landmarks().get(0).get(8).y() < handLandmarks.get(0).landmarks().get(0).get(5).y() &&
                handLandmarks.get(0).landmarks().get(0).get(12).y() < handLandmarks.get(0).landmarks().get(0).get(9).y() &&
                handLandmarks.get(0).landmarks().get(0).get(16).y() < handLandmarks.get(0).landmarks().get(0).get(13).y() &&
                handLandmarks.get(0).landmarks().get(0).get(20).y() < handLandmarks.get(0).landmarks().get(0).get(17).y())
            System.out.println("Hand is Open");
        else
        {
            System.out.println("Index finger is down");
        }
    }

    private static void StartModels(List<HandLandmarkerResult> handLandmarks)
    {
        IsHandOpen(handLandmarks);
    }

    public static void ProcessGestureRecognition(List<HandLandmarkerResult> handLandmarks)
    {
        if(HandsCount(handLandmarks)>0) {
            System.out.println(HandsCount(handLandmarks)+"Hands Detected!!!");
            StartModels(handLandmarks);
        }
        else
        {
            System.out.println("No Hands Detected!!!");
        }
    }

}
