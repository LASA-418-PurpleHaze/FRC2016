/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lasa.frc2016.statics;

import com.ni.vision.NIVision.Range;

/**
 *
 * @author LASA Robotics
 */
public class HazyConstant {
    public static int NIVISION_IMAGE_BORDER_SIZE = 480;
    public static Range HAZY_HUE_RANGE = new Range(75, 123);
    public static Range HAZY_SATURATION_RANGE = new Range(12, 123);
    public static Range HAZY_LUMINENCE_RANGE = new Range(122, 232);
}
