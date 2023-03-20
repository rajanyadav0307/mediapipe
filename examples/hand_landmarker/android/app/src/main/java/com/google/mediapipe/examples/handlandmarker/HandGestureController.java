package com.google.mediapipe.examples.handlandmarker;

import com.google.mediapipe.examples.handlandmarker.fragment.CameraFragment;
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

    private static boolean IsHandOpen(List<HandLandmarkerResult> handLandmarks)
    {
        if(handLandmarks.get(0).landmarks().get(0).get(8).y() < handLandmarks.get(0).landmarks().get(0).get(5).y() &&
                handLandmarks.get(0).landmarks().get(0).get(12).y() < handLandmarks.get(0).landmarks().get(0).get(9).y() &&
                handLandmarks.get(0).landmarks().get(0).get(16).y() < handLandmarks.get(0).landmarks().get(0).get(13).y() &&
                handLandmarks.get(0).landmarks().get(0).get(20).y() < handLandmarks.get(0).landmarks().get(0).get(17).y()) {
            //System.out.println("Hand is Open");
            return true;
        }
        else
        {
            System.out.println("Hand is Closed");
            return false;
        }
    }
    private static Double FindDistance(double px1, double py1, double px2, double py2)
    {
        double x_delta = px2-px1;
        double y_delta = 0;

        return (Math.sqrt((x_delta*x_delta)+(y_delta*y_delta)));
    }

    private static void StartModels(List<HandLandmarkerResult> handLandmarks)
    {
        if(IsHandOpen(handLandmarks)==true) {
            double point_x = handLandmarks.get(0).landmarks().get(0).get(20).x() * ((Integer) OverlayView.m_width) * (((Float)OverlayView.m_scalefactor));
            //double x_distance = FindDistance(0.0, 0.0, point_x, 0.0);
            double x_lo= ((double) CameraFragment.x_nav);
            //System.out.println(x_distance);
            double per_change = ((Math.abs(x_lo-point_x))/x_lo)*100;
            if( x_lo !=0.0 && point_x < x_lo && per_change > 25.0 )
            {
              CameraFragment.x_navright = 0;
              CameraFragment.x_navleft =   ((Integer) CameraFragment.x_navleft) +1;
            }
            else if ( x_lo !=0.0 && point_x > x_lo && per_change > 25.0)
            {
                CameraFragment.x_navleft = 0;
                CameraFragment.x_navright =   ((Integer) CameraFragment.x_navright) +1;
            }
            CameraFragment.x_count = ((Integer) CameraFragment.x_count)+1;
            CameraFragment.x_nav = point_x;
            if(CameraFragment.x_count.equals(20))
            {
                Integer left = ((Integer) CameraFragment.x_navleft);
                Integer right = ((Integer) CameraFragment.x_navright);
                if(left > right)
                {
                    System.out.println("Moved Left");
                }
                else if(right > left)
                {
                    System.out.println("Moved Right");
                }
                CameraFragment.x_navleft =0;
                CameraFragment.x_navright=0;
                CameraFragment.x_count = 0;
                CameraFragment.x_nav = 0.0;
            }
        }


    }

    public static void ProcessGestureRecognition(List<HandLandmarkerResult> handLandmarks)
    {
        if(HandsCount(handLandmarks)>0) {
            //System.out.println(HandsCount(handLandmarks)+"Hands Detected!!!");
            StartModels(handLandmarks);

        }
        else
        {
            System.out.println("No Hands Detected!!!");
        }
    }

}
