package main.java.gov.nist.isg.tracking.app.gui.swingworkers;

import java.io.File;

import javax.swing.*;

import main.java.gov.nist.isg.tracking.app.TrackingAppParams;
import main.java.gov.nist.isg.tracking.lib.Log;

/**
 * Created by mmajursk on 5/22/2014.
 */
public class LoadParamsSwingWorker extends SwingWorker<Void, Void> {

  @Override
  protected Void doInBackground() {
    Log.debug("Loading Parameters from file.");

    TrackingAppParams params = TrackingAppParams.getInstance();
    JProgressBar progressBar = params.getGuiPane().getControlPanel().getProgressBar();
    progressBar.setString("Loading Parameters...");
    progressBar.setIndeterminate(true);

    JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setMultiSelectionEnabled(false);

    int val = chooser.showOpenDialog(params.getGuiPane());
    if (val == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();

      Log.debug("Loading Parameters!");
      if (params.loadParamsFromFile(file)) {

        // update the Gui with the new params
        params.pushParamsToGUI();
        Log.mandatory("Parameters Loaded from: " + file.getAbsolutePath());
        progressBar.setString("Parameters Loaded");
        progressBar.setIndeterminate(false);
      } else {
        progressBar.setString("Invalid Parameters Found");
        progressBar.setIndeterminate(false);
        Log.error(
            "Invalid Parameters Discovered While Loading Parameters from: " + file
                .getAbsolutePath());
      }
    } else {
      progressBar.setString("Parameter Load Aborted");
      progressBar.setIndeterminate(false);
    }

    return null;
  }



}
