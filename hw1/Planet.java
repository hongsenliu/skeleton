public class Planet {
  public double x;
  public double y;
  public double xVelocity;
  public double yVelocity;
  public double mass;
  public String img;
  public double xNetForce;
  public double yNetForce;
  public double xAccel;
  public double yAccel;

  public Planet(double x, double y, double xVelocity, double yVelocity, double mass, String img) {
    this.x = x;
    this.y = y;
    this.xVelocity = xVelocity;
    this.yVelocity = yVelocity;
    this.mass = mass;
    this.img = img;
  }

  public double calcDistance(Planet p) {
    double dx = x - p.x;
    double dy = y - p.y;
    return Math.sqrt(dx*dx + dy*dy);
  }

  public double calcPairwiseForce(Planet p) {
    final double G = 6.67e-11;
    return (G*mass*p.mass)/(calcDistance(p) * calcDistance(p));
  }

  public double calcPairwiseForceX(Planet p) {
    double dx = p.x - x;
    return calcPairwiseForce(p) * dx / calcDistance(p);
  }

  public double calcPairwiseForceY(Planet p) {
    double dy = p.y - y;
    return calcPairwiseForce(p) * dy / calcDistance(p);
  }

  public void setNetForce(Planet[] planets) {
    int N = planets.length;
    xNetForce = 0.0;
    yNetForce = 0.0;
    for (int i = 0; i < N; i++) {
      xNetForce += calcPairwiseForceX(planets[i]);
      yNetForce += calcPairwiseForceY(planets[i]);
    }
  }

  public void draw() {
    StdDraw.picture(x, y, img);
  }

  private void calcAccelerationX() {
     xAccel = xNetForce/mass;
  }

  private void calcAccelerationY() {
    yAccel = yNetForce/mass;
  }

  private void setVelocity(double dt) {
    xVelocity = xVelocity + dt * xAccel;
    yVelocity = yVelocity + dt * yAccel;
  }

  private void setPosition(double dt) {
    x = x + dt * xVelocity;
    y = y + dt * yVelocity;
  }

  public void update(double dt) {
    calcAccelerationX();
    calcAccelerationY();
    setVelocity(dt);
    setPosition(dt);
  }
}