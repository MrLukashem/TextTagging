package ui;

import com.studiesproject.utils.Log;
import com.studiesproject.utils.ResultCallback;
import com.sun.istack.internal.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by MrLukashem on 14.01.2017.
 */
public class MainWindow {
    private static final String TAG = "MainWindow";

    private JButton mTakipiButton;
    private JButton mInputFileButton;
    private JTextField mOutputTextField;
    private JButton mPerformButton;
    private JPanel mPanel;
    private JLabel mInputFilePath;
    private JLabel mTakipiExePath;

    private JFrame mFrame;
    private JFileChooser mTakipiPathChooser;
    private JFileChooser mInputPathChooser;

    private String mTakipiPath = "";
    private String mInputPath = "";
    private String mOutputName = "";

    private ResultCallback mCB = null;

    public MainWindow(@NotNull ResultCallback callback) {
        mCB = callback;
    }

    private void initChoosers() {
        String startPath;
        String osName = System.getProperty("os.name");

        if (osName.contains("Windows")) {
            startPath = "C:\\Users\\MrLukashem";
        } else if (osName.contains("Linux") || osName.contains("Ubuntu")) {
            startPath = ".";
        } else {
            startPath = ".";
        }

        mTakipiPathChooser = new JFileChooser(startPath);
        mInputPathChooser = new JFileChooser(startPath);
    }

    private void initButtonsListeners() {
        mTakipiButton.addActionListener((ActionEvent e ) -> {
            int returnVal = mTakipiPathChooser.showDialog(null, "Choose");

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                mTakipiPath = mTakipiPathChooser.getSelectedFile().getPath();
                mTakipiExePath.setText(mTakipiPath);
            }
        });

        mInputFileButton.addActionListener((ActionEvent e) -> {
            int returnVal = mInputPathChooser.showDialog(null, "Choose");

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                mInputPath = mInputPathChooser.getSelectedFile().getPath();
                mInputFilePath.setText(mInputPath);
            }
        });

        mPerformButton.addActionListener((ActionEvent e) -> {
            mOutputName = mOutputTextField.getText();

            mCB.callback(mTakipiPath, mInputPath, mOutputName);
            mFrame.dispose();
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void show() {
        mFrame = new JFrame();
        initButtonsListeners();
        initChoosers();

        mFrame.setContentPane(mPanel);
        mFrame.pack();
        mFrame.setLocationRelativeTo(null);
        mFrame.setVisible(true);
        mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mFrame.setMinimumSize(new Dimension(700, 600));
    }
}
