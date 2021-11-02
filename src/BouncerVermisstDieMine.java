import de.ur.mi.bouncer.apps.BouncerApp;
import de.ur.mi.bouncer.apps.BouncerLauncher;


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

    @Override
    public void bounce() {
        loadMap("Mine");
    }

    public static void main(String[] args) {
        BouncerLauncher.launch("BouncerVermisstDieMine");
    }
}