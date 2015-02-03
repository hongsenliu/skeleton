public class NBody {
  public static Planet getPlanet(In fin) {
    double x=0.0;
    double y=0.0;
    double xVelocity=0.0;
    double yVelocity=0.0;
    double mass=0.0;
    String img="";
    if(!fin.isEmpty()){
      x = fin.readDouble();
    }
    if(!fin.isEmpty()){
      y = fin.readDouble();
    }
    if(!fin.isEmpty()){
      xVelocity = fin.readDouble();
    }
    if(!fin.isEmpty()){
      yVelocity = fin.readDouble();
    }
    if(!fin.isEmpty()){
      mass = fin.readDouble();
    }
    if(!fin.isEmpty()){
      img = "images/"+fin.readString();
    }
    return new Planet(x, y, xVelocity, yVelocity, mass, img);
  }

  public static void main(String[] args) {
    double T;
    double dt;
    String filename;
    int N = 0;
    double radius = 0.0;
    if(args.length >= 3) {
      T = Double.parseDouble(args[0]);
      dt = Double.parseDouble(args[1]);
      filename = args[2];
      In fin = new In(filename);
      if(!fin.isEmpty()) {
        N = fin.readInt();
      }
      if (!fin.isEmpty()) {
        radius = fin.readDouble();
      }
      // reads planets info
      Planet[] planets = new Planet[N];
      for(int i = 0; i < N && !fin.isEmpty(); i++) {
        planets[i] = getPlanet(fin);
      }
      fin.close();
      // draw initial state
      StdDraw.setScale(-1.0 * radius, radius);
      StdDraw.picture(0.0, 0.0,"images/starfield.jpg");
      for(int i = 0; i < N; i++) {
        planets[i].draw();
      }

      // creating an animation by time
      StdAudio.loop("audio/2001.mid");
      for (double ct = 0.0; ct <= T; ct=ct+dt) {
        for (int i = 0; i < N; i++){
          Planet[] otherPlanets = new Planet[N-1];
          for(int j = 0, k = 0; j < N; j++) {
            if(j != i) {
              otherPlanets[k] = planets[j];
              k++;
            }
          }
          planets[i].setNetForce(otherPlanets);
          planets[i].update(dt);
        }
        StdDraw.picture(0.0, 0.0,"images/starfield.jpg");
        for(int i = 0; i < N; i++) {
          planets[i].draw();
        }
        StdDraw.show(10);
      }
      StdAudio.close();
      System.out.println(planets.length);
      System.out.printf("%.2e\n", radius);
      for(int i = 0; i < N; i++) {
        System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", planets[i].x, planets[i].y, planets[i].xVelocity, planets[i].yVelocity, planets[i].mass, planets[i].img);
      }

    } else {
      System.out.println("ERROR: NOT Enough inputs");
    }
  }
}