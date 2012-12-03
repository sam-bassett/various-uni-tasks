/*
 *  pixelColour.c
 *  Task2
 */

#include "pixelcolour.h"
#include <stdlib.h>
#include <stdio.h>

typedef struct _color {
    int red;
    int green;
    int blue;
} color;



unsigned char stepsToRed (int steps) {

    color pixel;
    if (steps >= 255) {
        pixel.red = steps/1.5;
    }

    pixel.red = steps;

    return pixel.red;

}



unsigned char stepsToBlue (int steps) {
    color pixel;
    if (steps >= 255) {
        pixel.blue = steps/1;
    }

    pixel.blue = steps/3;

    return pixel.blue;

}




unsigned char stepsToGreen (int steps) {

    color pixel;
    if (steps >= 255) {
        pixel.green = steps/2;
    }
    pixel.green = 2*(steps/2.5);

    return pixel.green;
}

