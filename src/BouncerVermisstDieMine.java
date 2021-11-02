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

    // In dieser Variable speichern wir die Anzahl der Felder des aktuell tiefsten Schachts
    private int maxMeasuredDepth = 0;
    // Mit dieser Variable merken wir uns, ob wir bereits einen der Schächte mit einem blauen Feld markiert haben
    private boolean firstShaftMarked = false;

    @Override
    public void bounce() {
        loadMap("Mine");
        // Die gesamte Logik unseres Programmes wird in einer ausgelagerten Methode implementiert
        findAndMarkDeepestShaft();
    }

    /*
     * Mit dieser Methode bewegt Bouncer sich bis zum Ende der Mine, bestimmt die Tiefe aller Schächte,
     * auf die er stößt und markiert den Eingang des tiefsten Schachts mit einem blauen Feld.
     */
    private void findAndMarkDeepestShaft() {
        // Solange Bouncer sich nach vorne bewegen kann ...
        while (bouncer.canMoveForward()) {
            // ... betritt er das Feld vor ihm ...
            bouncer.move();
            // ... und prüft, ob rechts von ihm ein Schacht ist. Falls das so ist ...
            if (bouncer.canMoveRight()) {
                // ... misst er die Tiefe des Schachts ...
                int depthOfCurrentShaft = measureDepth();
                // ... und markiert bei Bedarf das Feld, auf dem er steht (und entfernt dabei die zuletzt gesetzte Markierung)
                markShaftIfNecessary(depthOfCurrentShaft);
            }
        }
    }

    /*
     * In dieser Methode misst Bouncer die Tiefe des Schachts, vor dessen Eingang er steht, kehrt anschließend
     * zu seiner Ausgangsposition zurück und gibt den gemessenen Tiefenwert (Anzahl der Felder) zurück.
     *
     * Startbedingung: Bouncer steht direkt über einem Schacht und blickt nach Osten.
     * Ergebnis: Bouncer steht direkt über dem Schacht, blickt nach Norden und hat die Tiefe des Schachts bestimmt.
     */
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

    /*
     * Mit dieser Methode markiert Bouncer das aktuelle Feld, falls die als Parameter übergebene Tiefe
     * größer als alle anderen vorher gemessenen Werte ist.
     *
     * Startbedingung: Bouncer steht direkt über einem Schacht und blickt nach Norden.
     * Ergebnis: Bouncer steht direkt über dem Schacht. Falls dies der bisher tiefste Schacht ist, hat Bouncer das
     * Feld blau markiert und alle anderen Markierungen über weniger tiefen Schächten entfernt.
     */
    private void markShaftIfNecessary(int lastMeasuredDepth) {
        if (lastMeasuredDepth > maxMeasuredDepth) {
            bouncer.paintField(FieldColor.BLUE);
            maxMeasuredDepth = lastMeasuredDepth;
            if (firstShaftMarked) {
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

    /*
     * Bouncer bewegt sich bis zum letzten gesetzen Marker, entfernt diesen und kehrt zum aktuellen Marker zurück
     */
    private void clearLastMarker() {
        bouncer.move();
        moveToNextFieldOfColor(FieldColor.BLUE);
        bouncer.clearFieldColor();
        turnAround();
        moveToNextFieldOfColor(FieldColor.BLUE);
    }

    /*
     * Bouncer bewegt sich vorwärts, bis er auf einem Feld der als Parameter angegebenen Farbe ankommt.
     */
    private void moveToNextFieldOfColor(FieldColor color) {
        while (!bouncer.isOnFieldWithColor(color)) {
            bouncer.move();
        }
    }

    /*
     * Bouncer dreht sich ausgehend von seiner aktuellen Blickrichtung um 270° nach links, d.h. er schaut
     * anschließend "nach rechts".
     */
    private void turnRight() {
        turnAround();
        bouncer.turnLeft();
    }

    /*
     * Bouncer dreht sich ausgehend von seiner aktuellen Blickrichtung um 180° nach links, d.h. er hat
     * sich anschließend "umgedreht".
     */
    private void turnAround() {
        bouncer.turnLeft();
        bouncer.turnLeft();
    }

    public static void main(String[] args) {
        BouncerLauncher.launch("BouncerVermisstDieMine");
    }
}