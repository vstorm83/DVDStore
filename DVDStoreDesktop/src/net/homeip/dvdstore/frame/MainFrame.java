/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainFrame.java
 *
 * Created on Feb 23, 2010, 2:45:22 PM
 */
package net.homeip.dvdstore.frame;

// <editor-fold defaultstate="collapsed" desc="import">
import net.homeip.dvdstore.ApplicationContextUtil;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import net.homeip.dvdstore.dialog.AboutDialog;
import net.homeip.dvdstore.dialog.ActorDialog;
import net.homeip.dvdstore.dialog.ChangeCredentialsDialog;
import net.homeip.dvdstore.dialog.ChatDialog;
import net.homeip.dvdstore.dialog.ConfigurationDialog;
import net.homeip.dvdstore.dialog.DirectorDialog;
import net.homeip.dvdstore.dialog.MovieCatgoryDialog;
import net.homeip.dvdstore.dialog.ReportDialog;
import net.homeip.dvdstore.dialog.SupplierDialog;
import net.homeip.dvdstore.interceptor.GetSessionIdInterceptor;
import net.homeip.dvdstore.listener.ChangeCredentialsListener;
import net.homeip.dvdstore.listener.event.LogoutEvent;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import net.homeip.dvdstore.listener.LogoutListener;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class MainFrame extends javax.swing.JFrame implements MessageListener {

    private ResourceBundle bundle = java.util.ResourceBundle.getBundle("DVDStoreDesktop");
    private List<LogoutListener> logoutListeners = new LinkedList<LogoutListener>();
    private SimpleMessageListenerContainer msgCon;

    // <editor-fold defaultstate="collapsed" desc="addListener">
    public void addLogoutListener(LogoutListener logoutListener) {
        if (logoutListener == null) {
            throw new IllegalArgumentException("can't add null logoutListener");
        }
        if (logoutListeners.contains(logoutListener)) {
            return;
        }
        logoutListeners.add(logoutListener);
    }// </editor-fold>

    /** Creates new form MainFrame */
    public MainFrame(String loggedInUserName) {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        initComponents();

        lblUserName.setText(loggedInUserName);
        setLeftPaneButtonHover();
        initToggleButton();

        addLogoutListener(((LogoutListener) ApplicationContextUtil.getApplicationContext().getBean(
                "logoutListener")));
        listenToLogoutMessage();
        AboutDialog aboutDialog = new AboutDialog(this);
        aboutDialog.setVisible(true);
    }

    // <editor-fold defaultstate="collapsed" desc="JMS LogoutMessage">
    private void listenToLogoutMessage() {
        try {
            msgCon =
                    (SimpleMessageListenerContainer) ApplicationContextUtil.getApplicationContext().getBean(
                    "logoutMessageListenerContainer");
            msgCon.setMessageListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            EventQueue.invokeLater(new Runnable() {

                public void run() {
                    JOptionPane.showMessageDialog(MainFrame.this, "Không kết nối đc với JMS Server");
                    logout();
                }
            });
        }
    }

    public void onMessage(Message msg) {
        try {
            if (GetSessionIdInterceptor.getSessionId().equals(((TextMessage) msg).getText())) {
                EventQueue.invokeLater(new Runnable() {

                    public void run() {
                        JOptionPane.showMessageDialog(MainFrame.this, "Đăng nhập từ một máy khác");
                        logout();
                    }
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="setLeftPaneButtonHover">
    private void setLeftPaneButtonHover() {
        MouseAdapter mouseAdapter = new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                ((JButton) e.getSource()).setBorderPainted(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ((JButton) e.getSource()).setBorderPainted(false);
            }
        };
        btnBussiness.addMouseListener(mouseAdapter);
        btnGoods.addMouseListener(mouseAdapter);
        btnLogout.addMouseListener(mouseAdapter);
        btnStatistic.addMouseListener(mouseAdapter);
        btnUser.addMouseListener(mouseAdapter);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="IFrame">
    private JInternalFrame findIFrame(String iframeTitle) {
        for (JInternalFrame iframe : desktopPane.getAllFrames()) {
            if (iframe.getTitle().equals(iframeTitle)) {
                return iframe;
            }
        }
        return null;
    }

    private void activeIFrame(JInternalFrame iframe) {
        try {
            // TODO add your handling code here:
            iframe.setVisible(true);
            iframe.setSelected(true);
            iframe.setMaximum(true);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }
    }

    private void addNewIFrame(JInternalFrame iframe, final JToggleButton controlButton) {
        desktopPane.add(iframe);
        activeIFrame(iframe);

        controlButton.setVisible(true);
        controlButton.setSelected(true);

        iframe.addVetoableChangeListener(new VetoableChangeListener() {

            public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
                String properName = evt.getPropertyName();
                Object value = evt.getNewValue();

                if (properName.equals("selected") && value.equals(true)) {
                    controlButton.setVisible(true);
                    controlButton.setSelected(true);
                }

                if (properName.equals("closed") && value.equals(true)) {
                    controlButton.setVisible(false);
                    controlButton.setSelected(false);
                }
            }
        });
        iframe.addInternalFrameListener(new InternalFrameAdapter() {

            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {
                controlButton.setSelected(false);
            }
        });
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="logout">
    private void logout() {
        if (msgCon != null) {
            msgCon.shutdown();
        }
        dispose();
        createLoginFrame();
        dispatchLogoutEvent();
    }

    private void dispatchLogoutEvent() {
        LogoutEvent evt = new LogoutEvent(this);
        for (LogoutListener logoutListener : logoutListeners) {
            logoutListener.logoutPerformed(evt);
        }
    }

    private void createLoginFrame() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="initToggleButton">
    private void initToggleButton() {
        btnTogBussiness.setVisible(false);
        btnTogGoods.setVisible(false);
        btnTogStatistic.setVisible(false);
        btnTogUser.setVisible(false);

        btnTogBussiness.setComponentPopupMenu(pMnuBussiness);
        btnTogGoods.setComponentPopupMenu(pMnuGoods);
        btnTogStatistic.setComponentPopupMenu(pMnuStatistic);
        btnTogUser.setComponentPopupMenu(pMnuUser);
    }// </editor-fold>    

    private void activeImportCardPane() {
        String iframeTitle = bundle.getString("StatisticIFrame.title");
        JInternalFrame iframe = findIFrame(iframeTitle);
        if (iframe != null) {
            activeIFrame(iframe);
        } else {
            iframe = new StatisticIFrame(StatisticIFrame.IMPORT_CARD_TAB);
            addNewIFrame(iframe, btnTogStatistic);
        }
        ((StatisticIFrame) iframe).activeTab(StatisticIFrame.IMPORT_CARD_TAB);
    }

    // <editor-fold defaultstate="collapsed" desc="Cascade-Tile">
    private void cascading() {
        int x = 0, y = 0;
        int distance = 30;

        JInternalFrame[] frames = desktopPane.getAllFrames();
        JInternalFrame slcFrame = desktopPane.getSelectedFrame();

        if (frames.length == 0 || frames.length == 1) {
            return;
        }

        int slcIndex = 0;
        for (JInternalFrame iFrame : frames) {
            if (iFrame == slcFrame) {
                break;
            }
            slcIndex++;
        }


        int start = (slcIndex + 1) % frames.length;
        int next = start;

        do {
            if (!frames[next].isIcon()) {
                try {
                    frames[next].setMaximum(false);
                    frames[next].reshape(x, y, getWidth() * 3 / 4, getHeight() * 3 / 4);
                    frames[next].toFront();

                    x += distance;
                    y += distance;
                    if (x > getWidth()) {
                        x = 0;
                    }
                    if (y > getHeight()) {
                        y = 0;
                    }
                } catch (PropertyVetoException e) {
                    e.printStackTrace();
                }
            }
            next = (next + 1) % frames.length;
        } while (next != start);
    }

    private void tiling() {
        // count frames that aren't iconized
        int frameCount = 0;
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (!frame.isIcon()) {
                frameCount++;
            }
        }
        if (frameCount == 0) {
            return;
        }

        int rows = (int) Math.sqrt(frameCount);
        int cols = frameCount / rows;
        int extra = frameCount % rows;
        // number of columns with an extra row

        int width = getWidth() / cols;
        int height = getHeight() / rows;
        int r = 0;
        int c = 0;
        for (JInternalFrame frame : desktopPane.getAllFrames()) {
            if (!frame.isIcon()) {
                try {
                    frame.setMaximum(false);
                    frame.reshape(c * width, r * height, width, height);
                    r++;
                    if (r == rows) {
                        r = 0;
                        c++;
                        if (c == cols - extra) {
                            // start adding an extra row
                            rows++;
                            height = getHeight() / rows;
                        }
                    }
                } catch (PropertyVetoException e) {
                    e.printStackTrace();
                }
            }
        }
    }// </editor-fold>

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pMnuBussiness = new javax.swing.JPopupMenu();
        mnuItmCloseBussiness = new javax.swing.JMenuItem();
        mnuItmBussinessCascade = new javax.swing.JMenuItem();
        mnuItmBussinessTile = new javax.swing.JMenuItem();
        pMnuUser = new javax.swing.JPopupMenu();
        mnuItmCloseUser = new javax.swing.JMenuItem();
        mnuItmUserCascade = new javax.swing.JMenuItem();
        mnuItmUserTile = new javax.swing.JMenuItem();
        pMnuStatistic = new javax.swing.JPopupMenu();
        mnuItmCloseStatistic = new javax.swing.JMenuItem();
        mnuItmStatisticCascade = new javax.swing.JMenuItem();
        mnuItmStatisticTile = new javax.swing.JMenuItem();
        pMnuGoods = new javax.swing.JPopupMenu();
        mnuItmCloseGoods = new javax.swing.JMenuItem();
        mnuItmGoodsCascade = new javax.swing.JMenuItem();
        mnuItmGoodTile = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        btnImportCard = new javax.swing.JButton();
        btnCaculator = new javax.swing.JButton();
        btnConfiguration = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnTogBussiness = new javax.swing.JToggleButton();
        btnTogUser = new javax.swing.JToggleButton();
        btnTogStatistic = new javax.swing.JToggleButton();
        btnTogGoods = new javax.swing.JToggleButton();
        jPanel1 = new javax.swing.JPanel();
        btnGoods = new javax.swing.JButton();
        btnUser = new javax.swing.JButton();
        btnBussiness = new javax.swing.JButton();
        btnStatistic = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        lblUserName = new javax.swing.JLabel();
        desktopPane = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        javax.swing.JMenu mnuHethong = new javax.swing.JMenu();
        javax.swing.JMenuItem mnuItmNhapPhim = new javax.swing.JMenuItem();
        javax.swing.JSeparator jSeparator6 = new javax.swing.JSeparator();
        javax.swing.JMenuItem mnuItmDoiMatKhau = new javax.swing.JMenuItem();
        javax.swing.JMenuItem mnuItmLogout = new javax.swing.JMenuItem();
        javax.swing.JSeparator jSeparator2 = new javax.swing.JSeparator();
        javax.swing.JMenuItem mnuItmExit = new javax.swing.JMenuItem();
        javax.swing.JMenu mnuCongCuQuanLy = new javax.swing.JMenu();
        mnuKinhDoanh = new javax.swing.JMenuItem();
        mnuKhachHang = new javax.swing.JMenuItem();
        mnuThongKe = new javax.swing.JMenuItem();
        mnuKhoHang = new javax.swing.JMenuItem();
        mnuQuanLyKhac = new javax.swing.JMenu();
        mnuItmQuanLyLoaiPhim = new javax.swing.JMenuItem();
        mnuItmQuanLyDaoDien = new javax.swing.JMenuItem();
        mnuItmQuanLyDienVien = new javax.swing.JMenuItem();
        mnuItmQuanLyNhaCungCap = new javax.swing.JMenuItem();
        mnuItmQuanLyChat = new javax.swing.JMenuItem();
        javax.swing.JMenu mnuReport = new javax.swing.JMenu();
        javax.swing.JMenuItem mnuItmReport = new javax.swing.JMenuItem();
        javax.swing.JMenu mnuGiupDo = new javax.swing.JMenu();
        mnuItemHuongdan = new javax.swing.JMenuItem();
        javax.swing.JSeparator jSeparator4 = new javax.swing.JSeparator();
        mnuItmLienHe = new javax.swing.JMenuItem();

        mnuItmCloseBussiness.setText("Đóng cửa sổ");
        mnuItmCloseBussiness.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmCloseBussinessActionPerformed(evt);
            }
        });
        pMnuBussiness.add(mnuItmCloseBussiness);

        mnuItmBussinessCascade.setText("Cascade");
        mnuItmBussinessCascade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmBussinessCascadeActionPerformed(evt);
            }
        });
        pMnuBussiness.add(mnuItmBussinessCascade);

        mnuItmBussinessTile.setText("Tile");
        mnuItmBussinessTile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmBussinessTileActionPerformed(evt);
            }
        });
        pMnuBussiness.add(mnuItmBussinessTile);

        mnuItmCloseUser.setText("Đóng cửa sổ");
        mnuItmCloseUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmCloseUserActionPerformed(evt);
            }
        });
        pMnuUser.add(mnuItmCloseUser);

        mnuItmUserCascade.setText("Cascade");
        mnuItmUserCascade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmUserCascadeActionPerformed(evt);
            }
        });
        pMnuUser.add(mnuItmUserCascade);

        mnuItmUserTile.setText("Tile");
        mnuItmUserTile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmUserTileActionPerformed(evt);
            }
        });
        pMnuUser.add(mnuItmUserTile);

        mnuItmCloseStatistic.setText("Đóng cửa sổ");
        mnuItmCloseStatistic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmCloseStatisticActionPerformed(evt);
            }
        });
        pMnuStatistic.add(mnuItmCloseStatistic);

        mnuItmStatisticCascade.setText("Cascade");
        mnuItmStatisticCascade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmStatisticCascadeActionPerformed(evt);
            }
        });
        pMnuStatistic.add(mnuItmStatisticCascade);

        mnuItmStatisticTile.setText("Tile");
        mnuItmStatisticTile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmStatisticTileActionPerformed(evt);
            }
        });
        pMnuStatistic.add(mnuItmStatisticTile);

        mnuItmCloseGoods.setText("Đóng cửa sổ");
        mnuItmCloseGoods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmCloseGoodsActionPerformed(evt);
            }
        });
        pMnuGoods.add(mnuItmCloseGoods);

        mnuItmGoodsCascade.setText("Cascade");
        mnuItmGoodsCascade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmGoodsCascadeActionPerformed(evt);
            }
        });
        pMnuGoods.add(mnuItmGoodsCascade);

        mnuItmGoodTile.setText("Tile");
        mnuItmGoodTile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmGoodTileActionPerformed(evt);
            }
        });
        pMnuGoods.add(mnuItmGoodTile);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DVDStore");

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnImportCard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/CDadd32.png"))); // NOI18N
        btnImportCard.setText("Nhập phim");
        btnImportCard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImportCard.setFocusable(false);
        btnImportCard.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImportCard.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImportCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportCardActionPerformed(evt);
            }
        });
        jToolBar1.add(btnImportCard);

        btnCaculator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calc_32_hot.png"))); // NOI18N
        btnCaculator.setText("Máy tính");
        btnCaculator.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCaculator.setFocusable(false);
        btnCaculator.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCaculator.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCaculator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCaculatorActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCaculator);

        btnConfiguration.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tools.png"))); // NOI18N
        btnConfiguration.setText("Cài đặt");
        btnConfiguration.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfiguration.setFocusable(false);
        btnConfiguration.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConfiguration.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnConfiguration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigurationActionPerformed(evt);
            }
        });
        jToolBar1.add(btnConfiguration);
        jToolBar1.add(jSeparator1);

        btnTogBussiness.setFont(new java.awt.Font("Tahoma", 1, 12));
        btnTogBussiness.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/currency_dollar_small.png"))); // NOI18N
        btnTogBussiness.setText("Kinh doanh");
        btnTogBussiness.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTogBussiness.setFocusable(false);
        btnTogBussiness.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTogBussiness.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTogBussinessActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTogBussiness);

        btnTogUser.setFont(new java.awt.Font("Tahoma", 1, 12));
        btnTogUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/businessmen_small.png"))); // NOI18N
        btnTogUser.setText("Khách hàng");
        btnTogUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTogUser.setFocusable(false);
        btnTogUser.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTogUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTogUserActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTogUser);

        btnTogStatistic.setFont(new java.awt.Font("Tahoma", 1, 12));
        btnTogStatistic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/chart_small.png"))); // NOI18N
        btnTogStatistic.setText("Thống kê");
        btnTogStatistic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTogStatistic.setFocusable(false);
        btnTogStatistic.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTogStatistic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTogStatisticActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTogStatistic);

        btnTogGoods.setFont(new java.awt.Font("Tahoma", 1, 12));
        btnTogGoods.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/movie_small.png"))); // NOI18N
        btnTogGoods.setText("Kho hàng");
        btnTogGoods.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTogGoods.setFocusable(false);
        btnTogGoods.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTogGoods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTogGoodsActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTogGoods);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBackground(new java.awt.Color(217, 226, 226));

        btnGoods.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/movie.png"))); // NOI18N
        btnGoods.setText("Kho hàng");
        btnGoods.setBorderPainted(false);
        btnGoods.setContentAreaFilled(false);
        btnGoods.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGoods.setFocusPainted(false);
        btnGoods.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGoods.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGoods.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGoodsActionPerformed(evt);
            }
        });

        btnUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/businessmen.png"))); // NOI18N
        btnUser.setText("Khách hàng");
        btnUser.setBorderPainted(false);
        btnUser.setContentAreaFilled(false);
        btnUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUser.setFocusPainted(false);
        btnUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUser.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserActionPerformed(evt);
            }
        });

        btnBussiness.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/currency_dollar.png"))); // NOI18N
        btnBussiness.setText("Kinh doanh");
        btnBussiness.setBorderPainted(false);
        btnBussiness.setContentAreaFilled(false);
        btnBussiness.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBussiness.setFocusPainted(false);
        btnBussiness.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBussiness.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBussiness.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBussinessActionPerformed(evt);
            }
        });

        btnStatistic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/chart.png"))); // NOI18N
        btnStatistic.setText("Thống kê");
        btnStatistic.setBorderPainted(false);
        btnStatistic.setContentAreaFilled(false);
        btnStatistic.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnStatistic.setFocusPainted(false);
        btnStatistic.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnStatistic.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnStatistic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticActionPerformed(evt);
            }
        });

        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnLogout.setText("Đăng thoát");
        btnLogout.setBorderPainted(false);
        btnLogout.setContentAreaFilled(false);
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.setFocusPainted(false);
        btnLogout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        lblUserName.setFont(new java.awt.Font("Tahoma", 1, 12));
        lblUserName.setForeground(new java.awt.Color(0, 0, 204));
        lblUserName.setText("Hummer83"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblUserName))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnGoods, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnStatistic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(btnBussiness))))
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lblUserName)
                .addGap(18, 18, 18)
                .addComponent(btnBussiness)
                .addGap(28, 28, 28)
                .addComponent(btnUser)
                .addGap(34, 34, 34)
                .addComponent(btnStatistic)
                .addGap(38, 38, 38)
                .addComponent(btnGoods)
                .addGap(45, 45, 45)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);

        desktopPane.setBackground(new java.awt.Color(212, 208, 200));
        getContentPane().add(desktopPane, java.awt.BorderLayout.CENTER);

        mnuHethong.setBackground(new java.awt.Color(236, 233, 216));
        mnuHethong.setMnemonic('H');
        mnuHethong.setText("Hệ thống");

        mnuItmNhapPhim.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        mnuItmNhapPhim.setText("Nhập phim");
        mnuItmNhapPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmNhapPhimActionPerformed(evt);
            }
        });
        mnuHethong.add(mnuItmNhapPhim);
        mnuHethong.add(jSeparator6);

        mnuItmDoiMatKhau.setText("Đổi mật khẩu");
        mnuItmDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmDoiMatKhauActionPerformed(evt);
            }
        });
        mnuHethong.add(mnuItmDoiMatKhau);

        mnuItmLogout.setText("Đăng thoát");
        mnuItmLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmLogoutActionPerformed(evt);
            }
        });
        mnuHethong.add(mnuItmLogout);
        mnuHethong.add(jSeparator2);

        mnuItmExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        mnuItmExit.setMnemonic('o');
        mnuItmExit.setText("Thoát");
        mnuItmExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmExitActionPerformed(evt);
            }
        });
        mnuHethong.add(mnuItmExit);

        jMenuBar1.add(mnuHethong);

        mnuCongCuQuanLy.setMnemonic('C');
        mnuCongCuQuanLy.setText("Công cụ quản lý");

        mnuKinhDoanh.setText("Kinh doanh");
        mnuKinhDoanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuKinhDoanhActionPerformed(evt);
            }
        });
        mnuCongCuQuanLy.add(mnuKinhDoanh);

        mnuKhachHang.setText("Khách hàng");
        mnuKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuKhachHangActionPerformed(evt);
            }
        });
        mnuCongCuQuanLy.add(mnuKhachHang);

        mnuThongKe.setText("Thống kê");
        mnuThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuThongKeActionPerformed(evt);
            }
        });
        mnuCongCuQuanLy.add(mnuThongKe);

        mnuKhoHang.setText("Kho hàng");
        mnuKhoHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuKhoHangActionPerformed(evt);
            }
        });
        mnuCongCuQuanLy.add(mnuKhoHang);

        jMenuBar1.add(mnuCongCuQuanLy);

        mnuQuanLyKhac.setText("Quản lý khác");

        mnuItmQuanLyLoaiPhim.setText("Quản lý loại phim");
        mnuItmQuanLyLoaiPhim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmQuanLyLoaiPhimActionPerformed(evt);
            }
        });
        mnuQuanLyKhac.add(mnuItmQuanLyLoaiPhim);

        mnuItmQuanLyDaoDien.setText("Quản lý đạo diễn");
        mnuItmQuanLyDaoDien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmQuanLyDaoDienActionPerformed(evt);
            }
        });
        mnuQuanLyKhac.add(mnuItmQuanLyDaoDien);

        mnuItmQuanLyDienVien.setText("Quản lý diễn viên");
        mnuItmQuanLyDienVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmQuanLyDienVienActionPerformed(evt);
            }
        });
        mnuQuanLyKhac.add(mnuItmQuanLyDienVien);

        mnuItmQuanLyNhaCungCap.setText("Quản lý nhà cung cấp");
        mnuItmQuanLyNhaCungCap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmQuanLyNhaCungCapActionPerformed(evt);
            }
        });
        mnuQuanLyKhac.add(mnuItmQuanLyNhaCungCap);

        mnuItmQuanLyChat.setText("Quản lý Nick chat");
        mnuItmQuanLyChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmQuanLyChatActionPerformed(evt);
            }
        });
        mnuQuanLyKhac.add(mnuItmQuanLyChat);

        jMenuBar1.add(mnuQuanLyKhac);

        mnuReport.setMnemonic('B');
        mnuReport.setText("Báo cáo thống kê");

        mnuItmReport.setText("Cửa sổ báo cáo");
        mnuItmReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmReportActionPerformed(evt);
            }
        });
        mnuReport.add(mnuItmReport);

        jMenuBar1.add(mnuReport);

        mnuGiupDo.setMnemonic('G');
        mnuGiupDo.setText("Giúp đỡ");

        mnuItemHuongdan.setText("Hướng dẫn");
        mnuItemHuongdan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemHuongdanActionPerformed(evt);
            }
        });
        mnuGiupDo.add(mnuItemHuongdan);
        mnuGiupDo.add(jSeparator4);

        mnuItmLienHe.setText("Liên hệ");
        mnuItmLienHe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItmLienHeActionPerformed(evt);
            }
        });
        mnuGiupDo.add(mnuItmLienHe);

        jMenuBar1.add(mnuGiupDo);

        setJMenuBar(jMenuBar1);

        setBounds(0, 0, 1114, 732);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBussinessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBussinessActionPerformed
        // TODO add your handling code here:
        String iframeTitle = bundle.getString("BussinessIFrame.title");
        JInternalFrame iframe = findIFrame(iframeTitle);
        if (iframe == null) {
            iframe = new BussinessIFrame();
            addNewIFrame(iframe, btnTogBussiness);
        } else {
            activeIFrame(iframe);
        }
    }//GEN-LAST:event_btnBussinessActionPerformed

    private void btnUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserActionPerformed
        // TODO add your handling code here:
        String iframeTitle = bundle.getString("UserIFrame.title");
        JInternalFrame iframe = findIFrame(iframeTitle);
        if (iframe != null) {
            activeIFrame(iframe);
        } else {
            iframe = new UserIFrame();
            addNewIFrame(iframe, btnTogUser);
        }
    }//GEN-LAST:event_btnUserActionPerformed

    private void btnGoodsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGoodsActionPerformed
        // TODO add your handling code here:
        String iframeTitle = bundle.getString("GoodsIFrame.title");
        JInternalFrame iframe = findIFrame(iframeTitle);
        if (iframe != null) {
            activeIFrame(iframe);
        } else {
            iframe = new GoodsIFrame();
            addNewIFrame(iframe, btnTogGoods);
        }
    }//GEN-LAST:event_btnGoodsActionPerformed

    private void btnStatisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticActionPerformed
        // TODO add your handling code here:
        String iframeTitle = bundle.getString("StatisticIFrame.title");
        JInternalFrame iframe = findIFrame(iframeTitle);
        if (iframe != null) {
            activeIFrame(iframe);
        } else {
            iframe = new StatisticIFrame(StatisticIFrame.EXPORT_CARD_TAB);
            addNewIFrame(iframe, btnTogStatistic);
        }
    }//GEN-LAST:event_btnStatisticActionPerformed

    private void btnTogBussinessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTogBussinessActionPerformed
        JInternalFrame iframe = findIFrame(bundle.getString("BussinessIFrame.title"));
        activeIFrame(iframe);
        ((JToggleButton) evt.getSource()).setSelected(true);
    }//GEN-LAST:event_btnTogBussinessActionPerformed

    private void btnTogUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTogUserActionPerformed
        // TODO add your handling code here:
        JInternalFrame iframe = findIFrame(bundle.getString("UserIFrame.title"));
        activeIFrame(iframe);
        ((JToggleButton) evt.getSource()).setSelected(true);
    }//GEN-LAST:event_btnTogUserActionPerformed

    private void btnTogStatisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTogStatisticActionPerformed
        // TODO add your handling code here:
        JInternalFrame iframe = findIFrame(bundle.getString("StatisticIFrame.title"));
        activeIFrame(iframe);
        ((JToggleButton) evt.getSource()).setSelected(true);
    }//GEN-LAST:event_btnTogStatisticActionPerformed

    private void btnTogGoodsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTogGoodsActionPerformed
        // TODO add your handling code here:
        JInternalFrame iframe = findIFrame(bundle.getString("GoodsIFrame.title"));
        activeIFrame(iframe);
        ((JToggleButton) evt.getSource()).setSelected(true);
    }//GEN-LAST:event_btnTogGoodsActionPerformed

    private void mnuItmCloseBussinessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmCloseBussinessActionPerformed
        // TODO add your handling code here:
        JInternalFrame iframe = findIFrame(bundle.getString("BussinessIFrame.title"));
        try {
            iframe.setClosed(true);
        } catch (PropertyVetoException ex) {
        }
    }//GEN-LAST:event_mnuItmCloseBussinessActionPerformed

    private void mnuItmCloseUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmCloseUserActionPerformed
        // TODO add your handling code here:
        JInternalFrame iframe = findIFrame(bundle.getString("UserIFrame.title"));
        try {
            iframe.setClosed(true);
        } catch (PropertyVetoException ex) {
        }
    }//GEN-LAST:event_mnuItmCloseUserActionPerformed

    private void mnuItmCloseStatisticActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmCloseStatisticActionPerformed
        // TODO add your handling code here:
        JInternalFrame iframe = findIFrame(bundle.getString("StatisticIFrame.title"));
        try {
            iframe.setClosed(true);
        } catch (PropertyVetoException ex) {
        }
    }//GEN-LAST:event_mnuItmCloseStatisticActionPerformed

    private void mnuItmCloseGoodsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmCloseGoodsActionPerformed
        // TODO add your handling code here:
        JInternalFrame iframe = findIFrame(bundle.getString("GoodsIFrame.title"));
        try {
            iframe.setClosed(true);
        } catch (PropertyVetoException ex) {
        }
    }//GEN-LAST:event_mnuItmCloseGoodsActionPerformed

    private void mnuItmBussinessCascadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmBussinessCascadeActionPerformed
        // TODO add your handling code here:
        cascading();
    }//GEN-LAST:event_mnuItmBussinessCascadeActionPerformed

    private void mnuItmBussinessTileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmBussinessTileActionPerformed
        // TODO add your handling code here:
        tiling();
    }//GEN-LAST:event_mnuItmBussinessTileActionPerformed

    private void mnuItmUserCascadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmUserCascadeActionPerformed
        // TODO add your handling code here:
        cascading();
    }//GEN-LAST:event_mnuItmUserCascadeActionPerformed

    private void mnuItmUserTileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmUserTileActionPerformed
        // TODO add your handling code here:
        tiling();
    }//GEN-LAST:event_mnuItmUserTileActionPerformed

    private void mnuItmStatisticCascadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmStatisticCascadeActionPerformed
        // TODO add your handling code here:
        cascading();
    }//GEN-LAST:event_mnuItmStatisticCascadeActionPerformed

    private void mnuItmStatisticTileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmStatisticTileActionPerformed
        // TODO add your handling code here:
        tiling();
    }//GEN-LAST:event_mnuItmStatisticTileActionPerformed

    private void mnuItmGoodsCascadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmGoodsCascadeActionPerformed
        // TODO add your handling code here:
        cascading();
    }//GEN-LAST:event_mnuItmGoodsCascadeActionPerformed

    private void mnuItmGoodTileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmGoodTileActionPerformed
        // TODO add your handling code here:
        tiling();
    }//GEN-LAST:event_mnuItmGoodTileActionPerformed

    private void btnCaculatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCaculatorActionPerformed
        // TODO add your handling code here:
        try {
            Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\calc.exe");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnCaculatorActionPerformed

    private void mnuItmLienHeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmLienHeActionPerformed
        // TODO add your handling code here:
        AboutDialog dlAbout = new AboutDialog(this);
        dlAbout.setVisible(true);
    }//GEN-LAST:event_mnuItmLienHeActionPerformed

    private void mnuItemHuongdanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemHuongdanActionPerformed
        // TODO add your handling code here:
        File fHelp = new File(System.getProperty("user.dir") + "\\Help.chm");
        if (!fHelp.exists()) {
            return;
        }

        try {
            Desktop.getDesktop().open(fHelp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_mnuItemHuongdanActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        logout();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void mnuItmLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmLogoutActionPerformed
        // TODO add your handling code here:
        logout();
    }//GEN-LAST:event_mnuItmLogoutActionPerformed

    private void mnuItmExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmExitActionPerformed
        // TODO add your handling code here:
        dispatchLogoutEvent();
        System.exit(0);
    }//GEN-LAST:event_mnuItmExitActionPerformed

    private void mnuItmDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmDoiMatKhauActionPerformed
        // TODO add your handling code here:
        ChangeCredentialsDialog dialog = new ChangeCredentialsDialog(this);
        dialog.addChangeCredentialsListener((ChangeCredentialsListener) ApplicationContextUtil.getApplicationContext().getBean("changeCredentialsListener"));
        if (dialog.showDialog() == ChangeCredentialsDialog.DIALOG_OK) {
            lblUserName.setText(dialog.getNewUserName());
        }
    }//GEN-LAST:event_mnuItmDoiMatKhauActionPerformed

    private void mnuItmNhapPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmNhapPhimActionPerformed
        // TODO add your handling code here:
        activeImportCardPane();
    }//GEN-LAST:event_mnuItmNhapPhimActionPerformed

    private void btnImportCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportCardActionPerformed
        // TODO add your handling code here:
        activeImportCardPane();
    }//GEN-LAST:event_btnImportCardActionPerformed

    private void mnuItmQuanLyLoaiPhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmQuanLyLoaiPhimActionPerformed
        // TODO add your handling code here:
        MovieCatgoryDialog movCatDialog = new MovieCatgoryDialog(this);
        movCatDialog.setVisible(true);
    }//GEN-LAST:event_mnuItmQuanLyLoaiPhimActionPerformed

    private void mnuItmQuanLyDaoDienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmQuanLyDaoDienActionPerformed
        // TODO add your handling code here:
        DirectorDialog directorDialog = new DirectorDialog(this);
        directorDialog.setVisible(true);
    }//GEN-LAST:event_mnuItmQuanLyDaoDienActionPerformed

    private void mnuItmQuanLyDienVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmQuanLyDienVienActionPerformed
        // TODO add your handling code here:
        ActorDialog actorDialog = new ActorDialog(this);
        actorDialog.setVisible(true);
    }//GEN-LAST:event_mnuItmQuanLyDienVienActionPerformed

    private void mnuItmQuanLyNhaCungCapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmQuanLyNhaCungCapActionPerformed
        // TODO add your handling code here:
        SupplierDialog supplierDialog = new SupplierDialog(this);
        supplierDialog.setVisible(true);
    }//GEN-LAST:event_mnuItmQuanLyNhaCungCapActionPerformed

    private void mnuItmQuanLyChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmQuanLyChatActionPerformed
        // TODO add your handling code here:
        ChatDialog chatDialog = new ChatDialog(this);
        chatDialog.setVisible(true);
    }//GEN-LAST:event_mnuItmQuanLyChatActionPerformed

    private void mnuKinhDoanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuKinhDoanhActionPerformed
        // TODO add your handling code here:
        btnBussinessActionPerformed(evt);
    }//GEN-LAST:event_mnuKinhDoanhActionPerformed

    private void mnuKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuKhachHangActionPerformed
        // TODO add your handling code here:
        btnUserActionPerformed(evt);
    }//GEN-LAST:event_mnuKhachHangActionPerformed

    private void mnuThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuThongKeActionPerformed
        // TODO add your handling code here:
        btnStatisticActionPerformed(evt);
    }//GEN-LAST:event_mnuThongKeActionPerformed

    private void mnuKhoHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuKhoHangActionPerformed
        // TODO add your handling code here:
        btnGoodsActionPerformed(evt);
    }//GEN-LAST:event_mnuKhoHangActionPerformed

    private void btnConfigurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigurationActionPerformed
        // TODO add your handling code here:
        ConfigurationDialog configurationDialog = new ConfigurationDialog(this);
        configurationDialog.setVisible(true);
    }//GEN-LAST:event_btnConfigurationActionPerformed

    private void mnuItmReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItmReportActionPerformed
        // TODO add your handling code here:
        ReportDialog reportDialog = new ReportDialog(this);
        reportDialog.setVisible(true);
    }//GEN-LAST:event_mnuItmReportActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBussiness;
    private javax.swing.JButton btnCaculator;
    private javax.swing.JButton btnConfiguration;
    private javax.swing.JButton btnGoods;
    private javax.swing.JButton btnImportCard;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnStatistic;
    private javax.swing.JToggleButton btnTogBussiness;
    private javax.swing.JToggleButton btnTogGoods;
    private javax.swing.JToggleButton btnTogStatistic;
    private javax.swing.JToggleButton btnTogUser;
    private javax.swing.JButton btnUser;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JMenuItem mnuItemHuongdan;
    private javax.swing.JMenuItem mnuItmBussinessCascade;
    private javax.swing.JMenuItem mnuItmBussinessTile;
    private javax.swing.JMenuItem mnuItmCloseBussiness;
    private javax.swing.JMenuItem mnuItmCloseGoods;
    private javax.swing.JMenuItem mnuItmCloseStatistic;
    private javax.swing.JMenuItem mnuItmCloseUser;
    private javax.swing.JMenuItem mnuItmGoodTile;
    private javax.swing.JMenuItem mnuItmGoodsCascade;
    private javax.swing.JMenuItem mnuItmLienHe;
    private javax.swing.JMenuItem mnuItmQuanLyChat;
    private javax.swing.JMenuItem mnuItmQuanLyDaoDien;
    private javax.swing.JMenuItem mnuItmQuanLyDienVien;
    private javax.swing.JMenuItem mnuItmQuanLyLoaiPhim;
    private javax.swing.JMenuItem mnuItmQuanLyNhaCungCap;
    private javax.swing.JMenuItem mnuItmStatisticCascade;
    private javax.swing.JMenuItem mnuItmStatisticTile;
    private javax.swing.JMenuItem mnuItmUserCascade;
    private javax.swing.JMenuItem mnuItmUserTile;
    private javax.swing.JMenuItem mnuKhachHang;
    private javax.swing.JMenuItem mnuKhoHang;
    private javax.swing.JMenuItem mnuKinhDoanh;
    private javax.swing.JMenu mnuQuanLyKhac;
    private javax.swing.JMenuItem mnuThongKe;
    private javax.swing.JPopupMenu pMnuBussiness;
    private javax.swing.JPopupMenu pMnuGoods;
    private javax.swing.JPopupMenu pMnuStatistic;
    private javax.swing.JPopupMenu pMnuUser;
    // End of variables declaration//GEN-END:variables
}
