package acceleratorphysics.numerical;

import java.util.ArrayList;

public class IVPSystem {

    private static ArrayList<IVPSystem> system;

    public IVPSystem(IVP... ivps){
        system = new ArrayList<>();
    }

    public void solve(IVPSolver solver, double time){
        system.forEach(ivp -> ivp.solve(solver, time));
    }

}
