import de.ur.mi.bouncer.apps.BouncerApp;
import de.ur.mi.bouncer.apps.BouncerLauncher;
import de.ur.mi.bouncer.world.fields.FieldColor;


/*
 * Bouncer befindet sich am Eingang einer Mine. Vor ihm zweigen verschiedene
 * Schächte ab. Bouncer soll den tiefsten Schaft finden und (nur) dessen Eingang
 * mit einem blauen Feld markieren und am Ende der Mine stehen bleiben.
 *
 * Startbedingung: Bouncer steht am Eingang der Mine und blickt nach Osten. Mögliche
 * Schächte beginnen direkt in der "Zeile" unter ihm. Auf dem ersten und dem letzten
 * Feld können sich keine Schächte befinden.
 *
 * Gewünschtes Ergebnis: Bouncer steht auf dem letzten Feld der Mine und blickt
 * nach Osten. Nur das Feld über dem tiefsten Schacht ist blau markiert.
 */
public class BouncerVermisstDieMine extends BouncerApp {

    private int maxMeasuredDepth = 0;
    private boolean firstShaftMarked = false;

    @Override
    public void bounce() {
        loadMap("Mine");
        findAndMarkDeepestShaft();
    }

    private void findAndMarkDeepestShaft() {
        while (bouncer.canMoveForward()) {
            bouncer.move();
            if (bouncer.canMoveRight()) {
                int depthOfCurrentShaft = measureDepth();
                markShaftIfNecessary(depthOfCurrentShaft);
            }
        }
    }

    private int measureDepth() {
        int depth = 0;
        turnRight();
        while (bouncer.canMoveForward()) {
            bouncer.move();
            depth++;
        }
        turnAround();
        while (bouncer.canNotMoveRight()) {
            bouncer.move();
        }
        return depth;
    }

    private void markShaftIfNecessary(int lastMeasuredDepth) {
        if (lastMeasuredDepth > maxMeasuredDepth) {
            bouncer.paintField(FieldColor.BLUE);
            maxMeasuredDepth = lastMeasuredDepth;
            if (firstShaftMarked == true) {
                bouncer.turnLeft();
                clearLastMarker();
            } else {
                firstShaftMarked = true;
                turnRight();
            }
        } else {
            turnRight();
        }
    }

    private void clearLastMarker() {
        bouncer.move();
        while (!bouncer.isOnFieldWithColor(FieldColor.BLUE)) {
            bouncer.move();
        }
        bouncer.clearFieldColor();
        turnAround();
        while (!bouncer.isOnFieldWithColor(FieldColor.BLUE)) {
            bouncer.move();
        }
    }

    private void turnRight() {
        turnAround();
        bouncer.turnLeft();
    }

    private void turnAround() {
        bouncer.turnLeft();
        bouncer.turnLeft();
    }

    public static void main(String[] args) {
        BouncerLauncher.launch("BouncerVermisstDieMine");
    }
}