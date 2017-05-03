package acceleratorphysics;

import acceleratorphysics.fields.EMField;
import acceleratorphysics.particles.ChargedParticle;
import acceleratorphysics.util.Vector3;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static acceleratorphysics.util.Vector3Test.assertVectorsEqual;
import static acceleratorphysics.util.Vector3Test.randomVector;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ParticleAcceleratorTest {

    private static final double TOL = 1E-10;

    private static EMField mockField;
    private static ChargedParticle mockParticle;

    private static Vector3 E, B;
    private static Vector3 r, v;

    private static double q, t, m;

    private static ParticleAccelerator accelerator;

    @Before
    public void setUp() throws Exception {

        Random random = new Random();

        // Create mock EMField and ChargedParticle
        E = randomVector();
        B = randomVector();
        r = randomVector();
        v = randomVector();
        q = random.nextDouble();
        m = random.nextDouble();
        t = random.nextDouble();

        Vector3 expectedForce = v.cross(B).add(E).scale(q);

        // Stub required particle & field methods
        mockParticle = mock(ChargedParticle.class);
        when(mockParticle.getAcceleration(any(Vector3.class)))
                .thenReturn(expectedForce.scale(1.0/m));

        when(mockParticle.getCharge())
                .thenReturn(q);

        mockField = mock(EMField.class);
        when(mockField.lorentzForce(q,v,r,t))
                .thenReturn(expectedForce);

        // Create accelerator
        accelerator = new ParticleAccelerator(mockParticle, mockField);

    }

    @Test
    public void acceleration() throws Exception {

        Vector3 expectedForce = v.cross(B).add(E).scale(q);

        Vector3 actual = accelerator.f(new Vector3[]{r,v}, t);
        Vector3 expected = expectedForce.scale(1.0/m);

        assertVectorsEqual("Acceleration:", actual, expected, TOL);

        verify(mockParticle).getAcceleration(expectedForce);
        verify(mockField).lorentzForce(q,v,r,t);
    }

}