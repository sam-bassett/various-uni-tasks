
/*
  COMP1917 Task 2: Mandelbrot Fractal Server
  By Sam Bassett and David Zhou
  Credit for web server to Richard Buckland
*/

#include <stdio.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <string.h>
#include <assert.h>
#include <unistd.h>
#include <math.h>
#include "pixelcolour.h"

//Webserver definitions
#define SIMPLE_SERVER_VERSION 1.0
#define REQUEST_BUFFER_SIZE 1000
#define DEFAULT_PORT 7191
#define NUMBER_OF_PAGES_TO_SERVE 100

/*
  ;;testing absence of pixelColour.h
unsigned char stepsToRed (int steps);
unsigned char stepsToBlue (int steps);
unsigned char stepsToGreen (int steps);
*/

//domain and range of graph for bmp
#define MIN_RE -1.5
#define MAX_RE 1.5
#define MIN_IM -1.5
#define MAX_IM 1.5

//bmp dimensions
#define IMAGE_HEIGHT 512
#define IMAGE_WIDTH 512

//iterations to test a given complex point
#define MAX_ITERATIONS 255

#define TRUE 1
#define FALSE 0
#define PIXEL_NUM IMAGE_HEIGHT*IMAGE_WIDTH
#define STEPS 0.007
#define SIZE_OF_BMP (54 + IMAGE_HEIGHT*IMAGE_WIDTH*4)

typedef struct _color {
    int red;
    int green;
    int blue;
} color;

//Webserver declarations
int waitForConnection (int serverSocket);
int makeServerSocket (int portno);
void serveHTML (int socket);
int httpServer ();

int webServerMain(void);
int isMandelbrot (float cRe, float cIm);
void printMandelbrot (float cRe, float cIm, int zoom, int socket);

/*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*/
/* MAIN
   runs the httpServer function (makes server)  */
int main (int argc, char* argv[]) {

    httpServer();

    return EXIT_SUCCESS;
}

/*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*/
/* FUNCTION: isMandelbrot
   Testing if a point is in the mandelbrot set
   returns ITERATIONS as the amount of tries to escape! */
int isMandelbrot(float cRe, float cIm) {

    int iterations = 0;
    float zRe = cRe;
    float zIm = cIm;
    float prevRe = 0;
    float prevIm = 0;
    float swapVar = 0;
    int condition = 1;
    //condition is an exit switch when an outcome is made
    //This array stores whether a given number is is the set
    //and the number of iterations it took to get there
    //int returnValue[2];
    int inSet = 0;
    char steps = 0;

    while (condition == 1) {

        swapVar = cRe + prevRe*prevRe - prevIm*prevIm;
        prevIm = cIm + 2*prevRe*prevIm;
        prevRe = swapVar;

        if (prevRe*prevRe + prevIm*prevIm > 4) {

            inSet = 0;
            //returnValue[0] = 0;
            //returnValue[1] = iterations;
            condition = 0;
        }
        if (iterations > MAX_ITERATIONS) {

            steps = MAX_ITERATIONS;
            inSet = 1;
            //returnValue[0] = 1;
            //returnValue[1] = MAX_ITERATIONS;
            condition = 0;
        }

        zRe = prevRe*prevRe - prevIm*prevIm + cRe;
        zIm = 2*prevIm*prevRe + cIm;

        iterations = iterations+1;

    }

    return iterations;

}

/*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*/
/* VOID FUNCTION: printMandelbrot
   scales a point depending on zoom level and coordinates!
   test each point through the isMandelbrot function!
   prints the result into the bmp data on server! */
void printMandelbrot (float cRe, float cIm, int zoom, int socket) {

   int counter = 0;
   float zoomIndex = 1;
   while (counter < zoom){
        zoomIndex = zoomIndex*2;
        counter++;
   }

   //range is increment change for pixel based on zoom
   float rangeRe = ((MAX_RE-MIN_RE)/zoomIndex)/IMAGE_WIDTH;
   float rangeIm = ((MAX_IM-MIN_IM)/zoomIndex)/IMAGE_HEIGHT;
   //double rangeRe, rangeIm;
   //rangeRe = 0.0097656;
   //rangeIm = 0.007;

   //double stepRe = rangeRe/IMAGE_WIDTH;
   //double stepIm = rangeIm/IMAGE_HEIGHT;

    float startRe = (cRe - ((MAX_RE-MIN_RE)/zoomIndex)/2);
    float startIm = (cIm - ((MAX_IM-MIN_IM)/zoomIndex)/2);
    printf("starts at x=%f, y=%f\n", startRe,startIm);
   float x = 0;
   float y = 0;


   char *message;

   message = "HTTP/1.0 200 OK\r\n"
                "Content-Type: image/bmp\r\n"
                //"Content-Size: 1048630\r\n"
                "\r\n";
   printf ("about to send=> %s\n", message);
   write (socket, message, strlen (message));

   //Header code of a proper 512x512 pixel bitmap
   char headerArray[54] = {'B','M',54,00,16,00,0,0,0,0,54,0,0,0,40,0,0,0,00,
       02,00,00,00,-2,-1,-1,1,0,32,00,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
       0,0,0,0};

   //writing the header to the server
   write(socket, headerArray, 54);


   printf("about to send the bmp\n");

   //char pixelArray[1048576];
   char pixelArray[4];

    while (y < IMAGE_HEIGHT) {

        while (x < IMAGE_WIDTH) {

            int steps = isMandelbrot(startRe, startIm);

            if (steps > 255) {
                pixelArray[0] = 0;
                pixelArray[1] = 0;
                pixelArray[2] = 0;
                pixelArray[3] = -1;
            } else {
                pixelArray[0] = stepsToGreen(steps);
                pixelArray[1] = stepsToBlue(steps);
                pixelArray[2] = stepsToRed(steps);
                pixelArray[3] = -1;
            }

            /*
               Testing archive
                pixelArray[0] = 0;
                pixelArray[1] = 0;
                pixelArray[2] = 0;
                pixelArray[3] = -1;*/
            /*char alpha = -1;
            write(socket, *stepsToBlue(steps), 1);
            sprintf(str, "%c", stepsToBlue(steps));
            write(socket, *stepsToGreen(steps), 1);
            sprintf(str, "%c", stepsToGreen(steps));
            write(socket, *stepsToRed(steps), 1);
            sprintf(str, "%c", stepsToRed(steps));
            write(socket, *alpha, 1);
            sprintf(str, "%c", -1);*/


            startRe = startRe + rangeRe;

            write(socket, pixelArray, 4);

            //testing array
            //printf("%s",pixelArray);

            x++;

        }
        x = 0;
        startRe = (cRe - ((MAX_RE-MIN_RE)/zoomIndex)/2);
        startIm = startIm + rangeIm;
        y++;
    }





  // write (socket, pixelArray, 1048576);

   /*int counter = 0;
   while (counter < SIZE_OF_BMP) {

    write(socket, &bmpArray[counter],1);
    counter++;
    }*/

}

/*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*/

int httpServer(void) {

    printf ("************************************\n");
   printf ("Starting 'simple' server %f\n", SIMPLE_SERVER_VERSION);
   printf ("Gazing into the void of infinity since 2011\n");

   int serverSocket = makeServerSocket (DEFAULT_PORT);
   printf ("Access this server at http://localhost:%d/\n", DEFAULT_PORT);
   printf ("************************************\n");

   char request[REQUEST_BUFFER_SIZE];

   int numberServed = 0;
   while (numberServed < NUMBER_OF_PAGES_TO_SERVE) {

      printf ("*** So far served %d pages ***\n", numberServed);

      int connectionSocket = waitForConnection (serverSocket);
      // wait for a request to be sent from a web browser, open a new
      // connection for this conversation

      // read the first line of the request sent by the browser
      int bytesRead;
      bytesRead = read (connectionSocket, request, (sizeof request)-1);
      assert (bytesRead >= 0);
      // were we able to read any data from the connection?

      // print entire request to the console
      printf (" *** Received http request ***\n %s\n", request);

      //send the browser a simple html page using http
      printf (" *** Sending http response ***\n");
      //intercepts bmp address request
      float x;
      float y;
      int z;
      sscanf (request,"GET /X-%f %*1s %f %*1s %d", &x,&y,&z);
      printf("X = %f\nY = %f\n", x,y);
      printf("Zoom = %d\n", z);

      //begin serving the mandelbrot set;
      printMandelbrot(x,y,z,connectionSocket);

      //close the connection after sending the page- keep aust beautiful
      close(connectionSocket);

      numberServed++;
   }

   // close the server connection after we are done- keep aust beautiful
   printf ("** shutting down the server **\n");
   close (serverSocket);

   return EXIT_SUCCESS;
}


/* checkpoint: the rest is bucklands server code. */
/* checkpoint: the rest is bucklands server code */
/* checkpoint: the rest is bucklands server code */
/* checkpoint: the rest is bucklands server code */
/* checkpoint: the rest is bucklands server code */
/* checkpoint: the rest is bucklands server code, just understand it */

/*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*/
void serveHTML (int socket) {
   char* message;

   // first send the http response header

   // IMPORTANT
   // (if you write stings one after another like this on separate
   // lines the c compiler kindly joins them togther for you into
   // one long string)
   message = "HTTP/1.0 200 OK\r\n"
                "Content-Type: text/html\r\n"
                "\r\n";
   printf ("about to send=> %s\n", message);
   write (socket, message, strlen (message));

   // now send the contents of the web page to be displayed
   message = "<HTML><BODY><P>\n";
   printf ("about to send=> %s\n", message);
   write (socket, message, strlen (message));

   message = "<P>IM SORRY DAVE, IM AFRAID I CANT DO THAT\n";
   printf ("about to send=> %s\n", message);
   write (socket, message, strlen (message));

   message = "</BODY></HTML>\n";
   printf ("about to send=> %s\n", message);
   write (socket, message, strlen (message));
}



/*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*/
// start the server listening on the specified port number
int makeServerSocket (int portNumber) {

   // create socket
   int serverSocket = socket (AF_INET, SOCK_STREAM, 0);
   assert (serverSocket >= 0);
   // error opening socket

   // bind socket to listening port
   struct sockaddr_in serverAddress;
   bzero ((char *) &serverAddress, sizeof (serverAddress));

   serverAddress.sin_family      = AF_INET;
   serverAddress.sin_addr.s_addr = INADDR_ANY;
   serverAddress.sin_port        = htons (portNumber);

   // let the server start immediately after a previous shutdown
   int optionValue = 1;
   setsockopt (
      serverSocket,
      SOL_SOCKET,
      SO_REUSEADDR,
      &optionValue,
      sizeof(int)
   );

   int bindSuccess =
      bind (
         serverSocket,
         (struct sockaddr *) &serverAddress,
         sizeof (serverAddress)
      );

   assert (bindSuccess >= 0);
   // if this assert fails wait a short while to let the operating
   // system clear the port before trying again

   return serverSocket;
}



/*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*;*/
// wait for a browser to request a connection,
// returns the socket on which the conversation will take place
int waitForConnection (int serverSocket) {
   // listen for a connection
   const int serverMaxBacklog = 10;
   listen (serverSocket, serverMaxBacklog);

   // accept the connection
   struct sockaddr_in clientAddress;
   socklen_t clientLen = sizeof (clientAddress);
   int connectionSocket =
      accept (
         serverSocket,
         (struct sockaddr *) &clientAddress,
         &clientLen
      );

   assert (connectionSocket >= 0);
   // error on accept

   return (connectionSocket);
}
