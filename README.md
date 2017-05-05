# acceleratorphysics
`acceleratorphysics` is a Java library for numerically solving classical particle trajectories in particle accelerators.
## Installation
Using Gradle:
```
  $ git clone https://github.com/sina-al/acceleratorphysics.git
```
change directory to repository and run
```
  $ ./gradlew build
```
the library jar file will be located at `acceleratorphysics/build/lib/acceleratorphysics.jar` which you can add to your classpath.
## Basic usage
Here we consider a non-relativistic proton with initial velocity 0.1 m/s in the i direction subjected to an orthogonal uniform magnetic field with magnetic flux density of 0.1E-7 T
### 1. Instantiate particle(s)
Define initial state. 
```java
State initialCondition = State.zero().velocity(Vector3.I.scale(0.1));
```
Instantiate a Newtonian (non-relativistic) particle from the corresponding `ParticleFactory`.
```java
ChargedParticle proton = ParticleFactory.Newtonian.proton(initialCondition);
```
### 2. Instantiate field
```java
EMField magneticField = new Uniform(Type.MAGNETIC, Vector3.J.scale(1E-7));
```
### 3. Instantiate initial value problem
```java
IVP protonInMagneticField = new ParticleAccelerator(proton, magneticField);
```
### 4. Solve the initial value problem
To mutate `proton` into its state after 10 seconds using Euler method:
```java
double time = 10;
IVPSolver eulerMethod = IVPSolver.euler();

protonInMagneticField.solve(eulerMethod, time);
```


This demonstrate the most basic usage of the API. See `acceleratorphysics/Demos/` for further demonstrations involving:
  * Accelerating bunches of particles.
  * Use of more sophisticated numerical algorithms such as RK4
  * Use of more intricate `EMField` objects such those representing a cyclotron.
  * Performing actions at each iteration such as tracking orbits and recording data.
  * Recording/Reading trajectories into/from binary data files.
  
  
## License
No license. This project was submitted as part of an assignment for a Java programming module in my final year of BSc Physics. (PHYS 389)
