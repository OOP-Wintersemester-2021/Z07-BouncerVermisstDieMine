import de.ur.mi.bouncer.apps.BouncerApp;
import de.ur.mi.bouncer.apps.BouncerLauncher;


public class BouncerVermisstDieMine extends BouncerApp {

    @Override
    public void bounce() {
        loadMap("Mine");
    }

    public static void main(String[] args) {
        BouncerLauncher.launch("BouncerVermisstDieMine");
    }
}