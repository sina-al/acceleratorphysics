package acceleratorphysics.numerical;

import com.sun.tools.javac.util.List;

import java.util.ArrayList;
import java.util.Arrays;

public class IVPSystem {

    private static ArrayList<IVPSystem> system;

    public IVPSystem(IVP... ivps){
        system = new ArrayList<>();
    }

    public void solve(IVPSolver solver, double time){
        system.forEach(ivp -> ivp.solve(solver, time));
    }

}
