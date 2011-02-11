/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GoodsIFrame.java
 *
 * Created on Feb 24, 2010, 6:44:54 PM
 */
package net.homeip.dvdstore.frame;

// <editor-fold defaultstate="collapsed" desc="import">
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import net.homeip.dvdstore.util.TextUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.atlanticbb.tantlinger.shef.HTMLEditorPane;
import net.homeip.dvdstore.ApplicationContextUtil;
import net.homeip.dvdstore.dialog.ActorDialog;
import net.homeip.dvdstore.dialog.DirectorDialog;
import net.homeip.dvdstore.dialog.MovieCatgoryDialog;
import net.homeip.dvdstore.listener.ActorChangeListener;
import net.homeip.dvdstore.listener.DirectorChangeListener;
import net.homeip.dvdstore.listener.MovieCatgoryChangeListener;
import net.homeip.dvdstore.listener.MovieChangeListener;
import net.homeip.dvdstore.listener.event.ActorChangeEvent;
import net.homeip.dvdstore.listener.event.DirectorChangeEvent;
import net.homeip.dvdstore.listener.event.MovieCatgoryChangeEvent;
import net.homeip.dvdstore.listener.event.MovieChangeEvent;
import net.homeip.dvdstore.listener.frame.GoodsIFrameListener;
import net.homeip.dvdstore.listener.ResultRetrievedListener;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.listener.event.LoadDataEvent;
import net.homeip.dvdstore.listener.event.ResultRetrievedEvent;
import net.homeip.dvdstore.listener.frame.UploadEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.pojo.webservice.vo.ActorVO;
import net.homeip.dvdstore.pojo.webservice.vo.DirectorVO;
import net.homeip.dvdstore.pojo.webservice.vo.GoodsMovieVO;
import net.homeip.dvdstore.pojo.webservice.vo.InitGoodsIFrameVO;
import net.homeip.dvdstore.swing.DSComboBoxModel;
import net.homeip.dvdstore.swing.renderer.DSTableCellRenderer;
import net.homeip.dvdstore.swing.ImagePreviewer;
import net.homeip.dvdstore.swing.GoodsMovieTableModel;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class GoodsIFrame extends javax.swing.JInternalFrame
        implements MovieCatgoryChangeListener, MovieChangeListener,
        DirectorChangeListener, ActorChangeListener {

    private HTMLEditorPane txtMovDesc;
    //Model
    private LoadDataModel<List<GoodsMovieVO>> goodsMovieModel;
    //Chỉ dùng để init, null ngay sau khi init
    private LoadDataModel<InitGoodsIFrameVO> initGoodsIFrameModel;
    private DataChangingModel goodsMovieChangingModel;
    //Upload
    private DataChangingModel uploadModel;
    //Listener
    private GoodsIFrameListener goodsIFrameListener;
    //Table
    private GoodsMovieTableModel goodsMovieTableModel;
    private String movNameSearch = "";
    //Sẽ được cài sau khi initData
    private MovieCatgoryVO movCatVOSearch = null;
    //State
    boolean restoreFormState = true;

    /** Creates new form GoodsIFrame */
    public GoodsIFrame() {
        initComponents();
        txtMovDesc = new HTMLEditorPane();
        jscrollMovDesc.setViewportView(txtMovDesc);
        prepareGoodsMovieTable();

        goodsIFrameListener =
                (GoodsIFrameListener) ApplicationContextUtil.getApplicationContext().getBean(
                "goodsIFrameListener");
        prepareListenerRegistry();//Register listener Registry with WindowAdapter

        prepareInitData();
        prepareLoadingData();
        prepareChangingData();
        prepareUpload();
    }

    // <editor-fold defaultstate="collapsed" desc="init Data">
    private void prepareInitData() {
        initGoodsIFrameModel = new LoadDataModel<InitGoodsIFrameVO>();
        initGoodsIFrameModel.setData(new InitGoodsIFrameVO());
        initGoodsIFrameModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onLoadInitData(evt);
            }
        });

        dispatchLoadInitDataEvent();
    }

    private void dispatchLoadInitDataEvent() {
        restoreFormState = false;
        saveState();
        setControlStatus(DISABLE_ALL);

        LoadDataEvent evt = new LoadDataEvent(this);
        evt.setLoadDataModel(initGoodsIFrameModel);
        List params = new ArrayList();
        params.add(movNameSearch);
        params.add(movCatVOSearch);
        evt.setParams(params);
        goodsIFrameListener.onLoadDataPerformed(evt);
    }

    private void onLoadInitData(ResultRetrievedEvent evt) {
        String errorMsg = checkError(initGoodsIFrameModel.getStatus());
        if (errorMsg != null) {
            JOptionPane.showMessageDialog(this, errorMsg);
        } else {
            initData();
            initGoodsIFrameModel = null;
        }
        restoreState(restoreFormState);
    }

    private void initData() {
        InitGoodsIFrameVO initGoodsIFrameVO = initGoodsIFrameModel.getData();
        goodsMovieTableModel.setRowData(initGoodsIFrameVO.getGoodsMovieVOs());
        cbxSearchByMovCat.setModel(new DSComboBoxModel(
                initGoodsIFrameVO.getMovieCatgoryVOs(), false));
        cbxMovCat.setModel(new DSComboBoxModel(initGoodsIFrameVO.getMovieCatgoryVOs(), false));
        cbxDirector.setModel(new DSComboBoxModel(initGoodsIFrameVO.getDirectorVOs(), true));

        movCatVOSearch = (MovieCatgoryVO) cbxSearchByMovCat.getSelectedItem();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="loadingData">
    private void prepareLoadingData() {
        goodsMovieModel = new LoadDataModel<List<GoodsMovieVO>>();
        goodsMovieModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onLoadMovie(evt);
            }
        });
    }

    private void onLoadMovie(ResultRetrievedEvent evt) {
        String errorMsg = checkError(goodsMovieModel.getStatus());
        if (errorMsg != null) {
            JOptionPane.showMessageDialog(this, errorMsg);
        } else {
            goodsMovieTableModel.setRowData(goodsMovieModel.getData());
        }
        restoreState(restoreFormState);
        this.restoreFormState = true;
    }

    private void dispatchLoadDataEvent() {
        saveState();
        setControlStatus(DISABLE_ALL);

        LoadDataEvent evt = new LoadDataEvent(this);
        evt.setLoadDataModel(goodsMovieModel);
        List params = new ArrayList();
        params.add(movNameSearch);
        params.add(movCatVOSearch);
        evt.setParams(params);
        goodsIFrameListener.onLoadDataPerformed(evt);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="changingData">
    private void prepareChangingData() {
        goodsMovieChangingModel = new DataChangingModel();
        goodsMovieChangingModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onChangingMovieResultRetrieved(evt);
            }
        });
    }
    private DataChangingEvent goodsMovieChangingEvent;

    private void dispatchChangingEvent(int action) {
        saveState();
        setControlStatus(DISABLE_ALL);

        goodsMovieChangingEvent = new DataChangingEvent(this);
        goodsMovieChangingEvent.setAction(action);
        goodsMovieChangingEvent.setDataChangingModel(goodsMovieChangingModel);
        switch (action) {
            case DataChangingEvent.ADD:
            case DataChangingEvent.MODIFY:
                GoodsMovieVO goodsMovieVO = getVOInForm();
                goodsMovieChangingEvent.setParam(goodsMovieVO);
                break;
            case DataChangingEvent.DELETE:
                goodsMovieChangingEvent.setParam(getSelectedIds());
        }
        goodsIFrameListener.onDataChanging(goodsMovieChangingEvent);
    }

    private void reDispatchChangingEvent(DataChangingEvent evt) {
        evt.setIgnoreReference(true);
        goodsIFrameListener.onDataChanging(evt);
    }

    private void dispatchChangedEvent() {
        this.restoreFormState = false;
        goodsIFrameListener.onDataChanged(new DataChangedEvent(this));
    }

    private void onChangingMovieResultRetrieved(ResultRetrievedEvent evt) {
        int status = goodsMovieChangingModel.getStatus();
        String errorMsg = checkError(status);
        errorMsg = errorMsg == null ? checkMovieChangingError(status) : errorMsg;
        if (errorMsg != null) {
            restoreState(true);
            JOptionPane.showMessageDialog(this, errorMsg);
        } else if (status == DataChangingModel.CONSTRAINT_VIOLATION) {
            if (JOptionPane.showConfirmDialog(this,
                    makeDBReferenceViolationMsg(goodsMovieChangingModel.getViolationName()),
                    "Xóa phim", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {
                reDispatchChangingEvent(goodsMovieChangingEvent);
            } else {
                restoreState(true);
            }
        } else {
            dispatchChangedEvent();
            JOptionPane.showMessageDialog(this, "Thao tác thành công");
        }
    }

    private String checkError(int status) {
        String errorMsg = null;
        switch (status) {
            case LoadDataModel.SERVER_CONNECTION_ERROR:
                errorMsg = "Lỗi kết nối Server";
                break;
            case LoadDataModel.SERVER_ERROR:
                errorMsg = "Lỗi Server";
                break;
        }
        return errorMsg;
    }

    private String checkMovieChangingError(int status) {
        String errorMsg = null;
        switch (status) {
            case DataChangingModel.DUPLICATE:
                errorMsg = "Trùng tên phim";
                break;
            case DataChangingModel.VALIDATE_FAIL:
                errorMsg = makeInvalidPropertiesMsg(goodsMovieChangingModel.getInvalidProperties());
                break;
        }
        return errorMsg;
    }// </editor-fold>

    public void onMovieCatgoryChange(MovieCatgoryChangeEvent evt) {
        resetMovCatComboBox(cbxSearchByMovCat, evt.getMovCatList());
        movCatVOSearch = (MovieCatgoryVO) cbxSearchByMovCat.getSelectedItem();
        resetMovCatComboBox(cbxMovCat, evt.getMovCatList());
        this.restoreFormState = true;
        dispatchLoadDataEvent();
    }

    private void resetMovCatComboBox(JComboBox comboBox, List<MovieCatgoryVO> movCatlist) {
        MovieCatgoryVO currentSelectedMovCat = (MovieCatgoryVO) comboBox.getSelectedItem();
        comboBox.setModel(new DSComboBoxModel(movCatlist, false));
        comboBox.setSelectedItem(currentSelectedMovCat);
    }

    public void onMovieChange(MovieChangeEvent evt) {
        dispatchLoadDataEvent();
    }

    public void onDirectorChange(DirectorChangeEvent evt) {
        DirectorVO currentSelectedDirectorVO = (DirectorVO) cbxDirector.getSelectedItem();
        cbxDirector.setModel(new DSComboBoxModel(evt.getDirectorList(), true));
        cbxDirector.setSelectedItem(currentSelectedDirectorVO);
        dispatchLoadDataEvent();
    }

    public void onActorChange(ActorChangeEvent evt) {
        List<ActorVO> currentActorVOs = getActorListFromString(txtActor.getText());
        List<ActorVO> newActorVOs = new ArrayList();
        if (evt.getActorList() != null) {
            int idx;
            for (ActorVO actorVO : currentActorVOs) {
                idx = evt.getActorList().indexOf(actorVO);
                if (idx != -1) {
                    newActorVOs.add(evt.getActorList().get(idx));
                }
            }
        }
        txtActor.setText(getActorStringFromList(newActorVOs));
        dispatchLoadDataEvent();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel goodsIFrameHeaderPane = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel lblTieuDe = new javax.swing.JLabel();
        movieListTabbedPane = new javax.swing.JTabbedPane();
        javax.swing.JSplitPane movieListSplitPane = new javax.swing.JSplitPane();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JPanel movieListPaneNorth = new javax.swing.JPanel();
        movieListScrollPane = new javax.swing.JScrollPane();
        javax.swing.JPanel movieListControlButtonPane = new javax.swing.JPanel();
        btnAddMovie = new javax.swing.JButton();
        btnDeleteMovie = new javax.swing.JButton();
        btnModifyMovie = new javax.swing.JButton();
        btnSearchMovie = new javax.swing.JButton();
        javax.swing.JLabel jLabel30 = new javax.swing.JLabel();
        txtSearchByMovName = new javax.swing.JTextField();
        javax.swing.JLabel jLabel31 = new javax.swing.JLabel();
        cbxSearchByMovCat = new javax.swing.JComboBox();
        javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
        javax.swing.JTabbedPane movieModifyTabbedPane = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel27 = new javax.swing.JLabel();
        javax.swing.JPanel jPanel5 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel19 = new javax.swing.JLabel();
        lblMovId = new javax.swing.JLabel();
        javax.swing.JLabel jLabel7 = new javax.swing.JLabel();
        txtMovName = new javax.swing.JTextField();
        javax.swing.JLabel jLabel8 = new javax.swing.JLabel();
        cbxMovCat = new javax.swing.JComboBox();
        btnOpenMovCatDialog = new javax.swing.JButton();
        javax.swing.JLabel jLabel15 = new javax.swing.JLabel();
        cbxDirector = new javax.swing.JComboBox();
        btnOpenDirectorDialog = new javax.swing.JButton();
        javax.swing.JLabel jLabel23 = new javax.swing.JLabel();
        txtMovPrice = new javax.swing.JTextField();
        javax.swing.JLabel jLabel22 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel24 = new javax.swing.JLabel();
        txtMovSaleOff = new javax.swing.JTextField();
        javax.swing.JLabel jLabel28 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel25 = new javax.swing.JLabel();
        dtDateCreated = new datechooser.beans.DateChooserCombo();
        javax.swing.JPanel jPanel7 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel21 = new javax.swing.JLabel();
        txtActor = new javax.swing.JTextField();
        javax.swing.JLabel jLabel26 = new javax.swing.JLabel();
        btnOpenActorDialog = new javax.swing.JButton();
        lblMovPic = new javax.swing.JLabel();
        btnChoosePic = new javax.swing.JButton();
        btnUploadPic = new javax.swing.JButton();
        jscrollMovDesc = new javax.swing.JScrollPane();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("DVDStoreDesktop"); // NOI18N
        setTitle(bundle.getString("GoodsIFrame.title")); // NOI18N

        goodsIFrameHeaderPane.setLayout(new java.awt.BorderLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/goctrai.gif"))); // NOI18N
        goodsIFrameHeaderPane.add(jLabel1, java.awt.BorderLayout.WEST);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/gocfai.gif"))); // NOI18N
        goodsIFrameHeaderPane.add(jLabel2, java.awt.BorderLayout.EAST);

        lblTieuDe.setBackground(new java.awt.Color(255, 255, 255));
        lblTieuDe.setFont(new java.awt.Font("Times New Roman", 3, 24));
        lblTieuDe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTieuDe.setText(bundle.getString("GoodsIFrame.lblTieuDe.text")); // NOI18N
        lblTieuDe.setOpaque(true);
        goodsIFrameHeaderPane.add(lblTieuDe, java.awt.BorderLayout.CENTER);

        getContentPane().add(goodsIFrameHeaderPane, java.awt.BorderLayout.NORTH);

        movieListSplitPane.setDividerLocation(200);
        movieListSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(100, 53));

        movieListPaneNorth.setPreferredSize(new java.awt.Dimension(100, 49));
        movieListPaneNorth.setLayout(new java.awt.BorderLayout());
        movieListPaneNorth.add(movieListScrollPane, java.awt.BorderLayout.CENTER);

        movieListControlButtonPane.setBackground(new java.awt.Color(255, 255, 255));

        btnAddMovie.setText(bundle.getString("GoodsIFrame.btnAddMovie.text")); // NOI18N
        btnAddMovie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMovieActionPerformed(evt);
            }
        });

        btnDeleteMovie.setText(bundle.getString("GoodsIFrame.btnDeleteMovie.text")); // NOI18N
        btnDeleteMovie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteMovieActionPerformed(evt);
            }
        });

        btnModifyMovie.setText(bundle.getString("GoodsIFrame.btnModifyMovie.text")); // NOI18N
        btnModifyMovie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyMovieActionPerformed(evt);
            }
        });

        btnSearchMovie.setText(bundle.getString("GoodsIFrame.btnSearchMovie.text")); // NOI18N
        btnSearchMovie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchMovieActionPerformed(evt);
            }
        });

        jLabel30.setText(bundle.getString("GoodsIFrame.jLabel30.text")); // NOI18N

        txtSearchByMovName.setText(bundle.getString("GoodsIFrame.txtSearchByMovName.text")); // NOI18N

        jLabel31.setText(bundle.getString("GoodsIFrame.jLabel31.text")); // NOI18N

        cbxSearchByMovCat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout movieListControlButtonPaneLayout = new javax.swing.GroupLayout(movieListControlButtonPane);
        movieListControlButtonPane.setLayout(movieListControlButtonPaneLayout);
        movieListControlButtonPaneLayout.setHorizontalGroup(
            movieListControlButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, movieListControlButtonPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearchByMovName, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxSearchByMovCat, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 348, Short.MAX_VALUE)
                .addComponent(btnSearchMovie)
                .addGap(18, 18, 18)
                .addComponent(btnAddMovie)
                .addGap(18, 18, 18)
                .addComponent(btnModifyMovie)
                .addGap(18, 18, 18)
                .addComponent(btnDeleteMovie)
                .addGap(28, 28, 28))
        );
        movieListControlButtonPaneLayout.setVerticalGroup(
            movieListControlButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(movieListControlButtonPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(movieListControlButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(movieListControlButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(btnModifyMovie)
                        .addComponent(btnAddMovie)
                        .addComponent(btnSearchMovie)
                        .addComponent(jLabel30)
                        .addComponent(txtSearchByMovName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel31)
                        .addComponent(cbxSearchByMovCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDeleteMovie))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        movieListPaneNorth.add(movieListControlButtonPane, java.awt.BorderLayout.SOUTH);

        jScrollPane1.setViewportView(movieListPaneNorth);

        movieListSplitPane.setTopComponent(jScrollPane1);

        jLabel27.setText(bundle.getString("GoodsIFrame.jLabel27.text")); // NOI18N

        jLabel19.setText(bundle.getString("GoodsIFrame.jLabel19.text")); // NOI18N

        lblMovId.setText(bundle.getString("GoodsIFrame.lblMovId.text")); // NOI18N

        jLabel7.setText(bundle.getString("GoodsIFrame.jLabel7.text")); // NOI18N

        jLabel8.setText(bundle.getString("GoodsIFrame.jLabel8.text")); // NOI18N

        cbxMovCat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnOpenMovCatDialog.setText(bundle.getString("GoodsIFrame.btnOpenMovCatDialog.text")); // NOI18N
        btnOpenMovCatDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenMovCatDialogActionPerformed(evt);
            }
        });

        jLabel15.setText(bundle.getString("GoodsIFrame.jLabel15.text")); // NOI18N

        cbxDirector.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnOpenDirectorDialog.setText(bundle.getString("GoodsIFrame.btnOpenDirectorDialog.text")); // NOI18N
        btnOpenDirectorDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenDirectorDialogActionPerformed(evt);
            }
        });

        jLabel23.setText(bundle.getString("GoodsIFrame.jLabel23.text")); // NOI18N

        jLabel22.setText(bundle.getString("GoodsIFrame.jLabel22.text")); // NOI18N

        jLabel24.setText(bundle.getString("GoodsIFrame.jLabel24.text")); // NOI18N

        jLabel28.setText(bundle.getString("GoodsIFrame.jLabel28.text")); // NOI18N

        jLabel25.setText(bundle.getString("GoodsIFrame.jLabel25.text")); // NOI18N

        dtDateCreated.setCurrentView(new datechooser.view.appearance.AppearancesList("Swing",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    true,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 255),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(128, 128, 128),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(255, 0, 0),
                    false,
                    false,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                (datechooser.view.BackRenderer)null,
                false,
                true)));
    dtDateCreated.setNothingAllowed(false);
    dtDateCreated.setFieldFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 12));
    dtDateCreated.setLocale(new java.util.Locale("vi", "", ""));
    dtDateCreated.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);

    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel19)
                .addComponent(jLabel15)
                .addComponent(jLabel23)
                .addComponent(jLabel24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addComponent(jLabel25))
            .addGap(18, 18, 18)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(dtDateCreated, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMovName, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMovId)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cbxDirector, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxMovCat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnOpenDirectorDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnOpenMovCatDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtMovSaleOff, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMovPrice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel22))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel28))))))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel5Layout.setVerticalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(lblMovId))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMovName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(cbxMovCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnOpenMovCatDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel15)
                        .addComponent(cbxDirector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel23)
                        .addComponent(txtMovPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel24)
                        .addComponent(txtMovSaleOff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel25)
                        .addComponent(dtDateCreated, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(193, 193, 193)
                    .addComponent(jLabel28))
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(155, 155, 155)
                    .addComponent(jLabel22))
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(111, 111, 111)
                    .addComponent(btnOpenDirectorDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(23, Short.MAX_VALUE))
    );

    jLabel21.setText(bundle.getString("GoodsIFrame.jLabel21.text")); // NOI18N

    txtActor.setEditable(false);
    txtActor.setText(bundle.getString("GoodsIFrame.txtActor.text")); // NOI18N

    jLabel26.setText(bundle.getString("GoodsIFrame.jLabel26.text")); // NOI18N

    btnOpenActorDialog.setText(bundle.getString("GoodsIFrame.btnOpenActorDialog.text")); // NOI18N
    btnOpenActorDialog.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnOpenActorDialogActionPerformed(evt);
        }
    });

    lblMovPic.setText(bundle.getString("GoodsIFrame.lblMovPic.text")); // NOI18N

    btnChoosePic.setText(bundle.getString("GoodsIFrame.btnChoosePic.text")); // NOI18N
    btnChoosePic.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnChoosePicActionPerformed(evt);
        }
    });

    btnUploadPic.setText(bundle.getString("GoodsIFrame.btnUploadPic.text")); // NOI18N
    btnUploadPic.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnUploadPicActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
    jPanel7.setLayout(jPanel7Layout);
    jPanel7Layout.setHorizontalGroup(
        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel7Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jLabel21)
                .addComponent(jLabel26)
                .addComponent(btnChoosePic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnUploadPic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addComponent(txtActor, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(2, 2, 2)
                    .addComponent(btnOpenActorDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(lblMovPic, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
            .addContainerGap())
    );
    jPanel7Layout.setVerticalGroup(
        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel7Layout.createSequentialGroup()
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel21)
                .addComponent(btnOpenActorDialog)
                .addComponent(txtActor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addComponent(jLabel26)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnChoosePic, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btnUploadPic, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(lblMovPic, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(31, Short.MAX_VALUE))
    );

    jscrollMovDesc.setMaximumSize(new java.awt.Dimension(100, 100));

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(jLabel27)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jscrollMovDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(13, Short.MAX_VALUE))
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel27)))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jscrollMovDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(17, Short.MAX_VALUE))
    );

    movieModifyTabbedPane.addTab(bundle.getString("GoodsIFrame.jPanel3.TabConstraints.tabTitle"), jPanel3); // NOI18N

    jScrollPane3.setViewportView(movieModifyTabbedPane);

    movieListSplitPane.setBottomComponent(jScrollPane3);

    movieListTabbedPane.addTab(bundle.getString("GoodsIFrame.movieListSplitPane.TabConstraints.tabTitle"), movieListSplitPane); // NOI18N

    getContentPane().add(movieListTabbedPane, java.awt.BorderLayout.CENTER);

    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((screenSize.width-1081)/2, (screenSize.height-666)/2, 1081, 666);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchMovieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchMovieActionPerformed
        // TODO add your handling code here:
        movNameSearch = txtSearchByMovName.getText();
        movCatVOSearch = (MovieCatgoryVO) cbxSearchByMovCat.getSelectedItem();
        this.restoreFormState = false;
        dispatchLoadDataEvent();
    }//GEN-LAST:event_btnSearchMovieActionPerformed

    private void btnAddMovieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMovieActionPerformed
        // TODO add your handling code here:
        if (btnAddMovie.getText().equals("Thêm mới")) {
            setControlStatus(ADD_NEW);
        } else {
            dispatchChangingEvent(DataChangingEvent.ADD);
        }
    }//GEN-LAST:event_btnAddMovieActionPerformed

    private void btnModifyMovieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyMovieActionPerformed
        dispatchChangingEvent(DataChangingEvent.MODIFY);

    }//GEN-LAST:event_btnModifyMovieActionPerformed

    private void btnDeleteMovieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteMovieActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this,
                "Xóa phim ?", "Xóa", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            dispatchChangingEvent(DataChangingEvent.DELETE);
        }
    }//GEN-LAST:event_btnDeleteMovieActionPerformed

    private void btnOpenMovCatDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenMovCatDialogActionPerformed
        // TODO add your handling code here:
        MovieCatgoryDialog movCatDialog = new MovieCatgoryDialog(null);
        movCatDialog.setVisible(true);
    }//GEN-LAST:event_btnOpenMovCatDialogActionPerformed

    private void btnOpenDirectorDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenDirectorDialogActionPerformed
        // TODO add your handling code here:
        DirectorDialog directorDialog = new DirectorDialog(null);
        directorDialog.setVisible(true);
    }//GEN-LAST:event_btnOpenDirectorDialogActionPerformed

    private void btnOpenActorDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActorDialogActionPerformed
        // TODO add your handling code here:
        ActorDialog actorDialog = new ActorDialog(null);
        if (actorDialog.showActorDialog(getActorListFromString(txtActor.getText()))
                == ActorDialog.DIALOG_OK) {
            txtActor.setText(getActorStringFromList(actorDialog.getChoosenActorVOs()));
        }
    }//GEN-LAST:event_btnOpenActorDialogActionPerformed
    private JFileChooser chooser;

    private void btnChoosePicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChoosePicActionPerformed
        // TODO add your handling code here:
        if (chooser == null) {
            makeFileChooser();
        }
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            setPic("file:///" + selectedFile.getAbsolutePath());
        }
    }//GEN-LAST:event_btnChoosePicActionPerformed

    private void btnUploadPicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadPicActionPerformed
        // TODO add your handling code here:
        if (chooser == null) {
            makeFileChooser();
        }
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            if (selectedFile.length() > 3072 * 1024L) {
                JOptionPane.showMessageDialog(this, "Không thể upload file > 3Mb");
                return;
            }
            try {
                byte[] bufferFile = new byte[Long.valueOf(selectedFile.length()).intValue()];
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                fileInputStream.read(bufferFile);
                dispatchUploadEvent(selectedFile.getName(), bufferFile);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnUploadPicActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddMovie;
    private javax.swing.JButton btnChoosePic;
    private javax.swing.JButton btnDeleteMovie;
    private javax.swing.JButton btnModifyMovie;
    private javax.swing.JButton btnOpenActorDialog;
    private javax.swing.JButton btnOpenDirectorDialog;
    private javax.swing.JButton btnOpenMovCatDialog;
    private javax.swing.JButton btnSearchMovie;
    private javax.swing.JButton btnUploadPic;
    private javax.swing.JComboBox cbxDirector;
    private javax.swing.JComboBox cbxMovCat;
    private javax.swing.JComboBox cbxSearchByMovCat;
    private datechooser.beans.DateChooserCombo dtDateCreated;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jscrollMovDesc;
    private javax.swing.JLabel lblMovId;
    private javax.swing.JLabel lblMovPic;
    private javax.swing.JScrollPane movieListScrollPane;
    private javax.swing.JTabbedPane movieListTabbedPane;
    private javax.swing.JTextField txtActor;
    private javax.swing.JTextField txtMovName;
    private javax.swing.JTextField txtMovPrice;
    private javax.swing.JTextField txtMovSaleOff;
    private javax.swing.JTextField txtSearchByMovName;
    // End of variables declaration//GEN-END:variables

    private void prepareListenerRegistry() {
        addInternalFrameListener(goodsIFrameListener);
    }
    // <editor-fold defaultstate="collapsed" desc="prepare table">
    private JTable tblGoodsMovie;

    private void prepareGoodsMovieTable() {
        tblGoodsMovie = new javax.swing.JTable();
        tblGoodsMovie.setAutoCreateRowSorter(true);
        tblGoodsMovie.setFillsViewportHeight(true);
        tblGoodsMovie.getTableHeader().setReorderingAllowed(false);
        movieListScrollPane.setViewportView(tblGoodsMovie);

        goodsMovieTableModel = new GoodsMovieTableModel();
        tblGoodsMovie.setModel(goodsMovieTableModel);

        tblGoodsMovie.getColumnModel().getColumn(0).setMaxWidth(100);
        tblGoodsMovie.getColumnModel().getColumn(1).setMaxWidth(800);
        tblGoodsMovie.getColumnModel().getColumn(2).setMaxWidth(300);
        tblGoodsMovie.getColumnModel().getColumn(3).setMaxWidth(100);
        tblGoodsMovie.getColumnModel().getColumn(4).setMaxWidth(200);
        tblGoodsMovie.getColumnModel().getColumn(5).setMaxWidth(100);
        tblGoodsMovie.getColumnModel().getColumn(6).setMaxWidth(200);
        tblGoodsMovie.setRowHeight(20);
        DSTableCellRenderer cellRenderer = new DSTableCellRenderer();
        tblGoodsMovie.setDefaultRenderer(Long.class, cellRenderer);
        tblGoodsMovie.setDefaultRenderer(String.class, cellRenderer);
        tblGoodsMovie.setDefaultRenderer(Double.class, cellRenderer);
        tblGoodsMovie.setDefaultRenderer(Integer.class, cellRenderer);
        tblGoodsMovie.setDefaultRenderer(Date.class, cellRenderer);

        tblGoodsMovie.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblGoodsMovie.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                int[] selectedRowIndex = tblGoodsMovie.getSelectedRows();
                switch (selectedRowIndex.length) {
                    case 0:
                        setControlStatus(ADD_NEW);
                        break;
                    case 1:
                        setControlStatus(UPDATE_DELETE);
                        setFormData(goodsMovieTableModel.getGoodsMovieVO(
                                tblGoodsMovie.convertRowIndexToModel(selectedRowIndex[0])));
                        break;
                    default:
                        setControlStatus(DELETE);
                }
            }
        });
        tblGoodsMovie.getRowSorter().addRowSorterListener(new RowSorterListener() {

            public void sorterChanged(RowSorterEvent e) {
                if (e.getType() == RowSorterEvent.Type.SORT_ORDER_CHANGED) {
                    lastSortKeys = e.getSource().getSortKeys();
                }
            }
        });
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Control Status">
    private static final int DISABLE_ALL = 0;
    private static final int UPDATE_DELETE = 1;
    private static final int DELETE = 2;
    private static final int ADD_NEW = 3;

    private void setControlStatus(int status) {
        switch (status) {
            case DISABLE_ALL:
                tblGoodsMovie.setEnabled(false);
                txtSearchByMovName.setEnabled(false);
                cbxSearchByMovCat.setEnabled(false);
                btnSearchMovie.setEnabled(false);
                //Control
                btnAddMovie.setEnabled(false);
                btnModifyMovie.setEnabled(false);
                btnDeleteMovie.setEnabled(false);
                setFormEnable(false);
                break;
            case ADD_NEW:
                btnAddMovie.setEnabled(true);
                btnAddMovie.setText("Đồng ý thêm");
                btnModifyMovie.setEnabled(false);
                btnDeleteMovie.setEnabled(false);
                tblGoodsMovie.clearSelection();
                setFormEnable(true);
                setFormData(null);
                break;
            case UPDATE_DELETE:
                btnAddMovie.setEnabled(true);
                btnAddMovie.setText("Thêm mới");
                btnModifyMovie.setEnabled(true);
                btnDeleteMovie.setEnabled(true);
                setFormEnable(true);
                break;
            case DELETE:
                btnAddMovie.setEnabled(true);
                btnAddMovie.setText("Thêm mới");
                btnModifyMovie.setEnabled(false);
                btnDeleteMovie.setEnabled(true);
                setFormEnable(false);
                setFormData(null);
        }
    }

    private void setFormEnable(boolean enabled) {
        txtMovName.setEnabled(enabled);
        cbxMovCat.setEnabled(enabled);
        cbxDirector.setEnabled(enabled);
        txtMovPrice.setEnabled(enabled);
        txtMovSaleOff.setEnabled(enabled);
        dtDateCreated.setEnabled(enabled);
        txtActor.setEnabled(enabled);
        btnOpenActorDialog.setEnabled(enabled);
        lblMovPic.setEnabled(enabled);
        btnChoosePic.setEnabled(enabled);
        txtMovDesc.setVisible(enabled);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="State">
    private List lastSortKeys;
    private List<Long> lastTblGoodsMovieSelectedId;
    private String lastTxtMovNameText;
    private Object lastCbxMovCatSelectedItem;
    private Object lastCbxDirectorSelectedItem;
    private String lastTxtMovPriceText;
    private String lastTxtMovSaleOffText;
    private Date lastDateCreated;
    private String lastTxtActorText;
    private String lastPicURL;
    private String currentPicURL;
    private String lastTxtMovDescText;

    private void setFormData(GoodsMovieVO goodsMovieVO) {
        Long movId = null;
        String movName = "";
        MovieCatgoryVO movCat = null;
        DirectorVO director = null;
        Double movPrice = null;
        Double movSaleOff = null;
        Date dateCreated = new Date();
        List<ActorVO> actors = null;
        String picURL = null;
        String movDesc = null;
        if (goodsMovieVO != null) {
            movId = goodsMovieVO.getMovId();
            movName = goodsMovieVO.getMovName();
            movCat = goodsMovieVO.getMovCatVO();
            director = goodsMovieVO.getDirectorVO();
            movPrice = goodsMovieVO.getMovPrice();
            movSaleOff = goodsMovieVO.getMovSaleOff();
            dateCreated = goodsMovieVO.getDateCreated();
            actors = goodsMovieVO.getActorVOs();
            picURL = goodsMovieVO.getMovPicURL();
            movDesc = goodsMovieVO.getMovDesc();
        }
        lblMovId.setText(movId == null ? "Thêm mới" : String.valueOf(movId));
        txtMovName.setText(movName);
        cbxMovCat.setSelectedItem(movCat);
        cbxDirector.setSelectedItem(director);
        txtMovPrice.setText(movPrice == null ? "" : String.valueOf(movPrice));
        txtMovSaleOff.setText(movSaleOff == null ? "" : String.valueOf(movSaleOff));
        Calendar selectedDate = dtDateCreated.getSelectedDate();
        selectedDate.setTime(dateCreated);
        dtDateCreated.setSelectedDate(selectedDate);
        txtActor.setText(getActorStringFromList(actors));
        setPic(picURL);
        txtMovDesc.setText(movDesc == null ? "" : movDesc);
    }

    // <editor-fold defaultstate="collapsed" desc="setPic">
    private void setPic(final String picURL) {
        currentPicURL = picURL;
        if (picURL == null) {
            lblMovPic.setIcon(null);
        } else {
            SwingWorker<ImageIcon, Void> loadPicThread = new SwingWorker<ImageIcon, Void>() {

                @Override
                protected ImageIcon doInBackground() throws Exception {
                    ImageIcon icon = null;
                    try {
                        icon = new ImageIcon(ImageIO.read(
                                new URL(TextUtil.transformURLSpace(picURL))).getScaledInstance(
                                132, 205, Image.SCALE_DEFAULT));
                    } catch (MalformedURLException ex) {                        
                    }
                    return icon;
                }

                @Override
                protected void done() {
                    try {
                        ImageIcon icon = get();
                        lblMovPic.setIcon(icon);
                        icon.setImageObserver(lblMovPic);
                    } catch (Exception ex) {                        
                        lblMovPic.setIcon(null);
                    }
                }
            };
            loadPicThread.execute();
        }
    }// </editor-fold>

    private void restoreState(boolean restoreFormState) {
        txtSearchByMovName.setEnabled(true);
        cbxSearchByMovCat.setEnabled(true);
        btnSearchMovie.setEnabled(true);

        tblGoodsMovie.setEnabled(true);
        tblGoodsMovie.clearSelection();

        tblGoodsMovie.getRowSorter().setSortKeys(lastSortKeys);
        for (Long id : lastTblGoodsMovieSelectedId) {
            for (int row = 0; row < tblGoodsMovie.getRowCount(); row++) {
                if (id.longValue() == ((Long) tblGoodsMovie.getValueAt(row, 0)).longValue()) {
                    tblGoodsMovie.addRowSelectionInterval(row, row);
                }
            }
        }

        if (tblGoodsMovie.getSelectedRow() == -1) {
            setControlStatus(ADD_NEW);
        }

        if (restoreFormState) {
            txtMovName.setText(lastTxtMovNameText);
            cbxMovCat.setSelectedItem(lastCbxMovCatSelectedItem);
            cbxDirector.setSelectedItem(lastCbxDirectorSelectedItem);
            txtMovPrice.setText(lastTxtMovPriceText);
            txtMovSaleOff.setText(lastTxtMovSaleOffText);
            Calendar selectedDate = dtDateCreated.getSelectedDate();
            selectedDate.setTime(lastDateCreated);
            dtDateCreated.setSelectedDate(selectedDate);
            txtActor.setText(lastTxtActorText);
            setPic(lastPicURL);
            txtMovDesc.setText(lastTxtMovDescText);
        }
    }

    private void saveState() {
        lastSortKeys = tblGoodsMovie.getRowSorter().getSortKeys();
        lastTblGoodsMovieSelectedId = getSelectedIds();
        lastTxtMovNameText = txtMovName.getText();
        lastCbxMovCatSelectedItem = cbxMovCat.getSelectedItem();
        lastCbxDirectorSelectedItem = cbxDirector.getSelectedItem();
        lastTxtMovPriceText = txtMovPrice.getText();
        lastTxtMovSaleOffText = txtMovSaleOff.getText();
        lastDateCreated = dtDateCreated.getSelectedDate().getTime();
        lastTxtActorText = txtActor.getText();
        lastPicURL = currentPicURL;
        lastTxtMovDescText = txtMovDesc.getText();
    }// </editor-fold>        

    // <editor-fold defaultstate="collapsed" desc="make">
    private String makeDBReferenceViolationMsg(List<String> violationNames) {
        if (violationNames == null) {
            return null;
        }
        StringBuilder msgBuilder = new StringBuilder("Các phim:");
        for (String violationName : violationNames) {
            msgBuilder.append(violationName);
            msgBuilder.append("\n");
        }
        msgBuilder.append("Còn trong phiếu nhập hoặc phiếu xuất, tiếp tục xóa ?");
        return msgBuilder.toString();
    }

    private String makeInvalidPropertiesMsg(Set<String> properties) {
        if (properties == null) {
            return null;
        }
        StringBuilder errorMsgBuilder = new StringBuilder();
        for (String property : properties) {
            if (property.equals("movName")) {
                errorMsgBuilder.append("Tên phim từ 1 đến 50 ký tự\n");
            } else if (property.equals("movCatVO")) {
                errorMsgBuilder.append("Chưa chọn loại phim\n");
            } else if (property.equals("movPrice")) {
                errorMsgBuilder.append("Giá bán chưa nhập hoặc sai định dạng\n");
            } else if (property.equals("movSaleOff")) {
                errorMsgBuilder.append("Giảm giá chưa nhập hoặc sai định dạng");
            }
        }
        return errorMsgBuilder.toString();
    }

    private void makeFileChooser() {
        chooser = new JFileChooser();

        chooser.setApproveButtonText("Select");
        chooser.setApproveButtonMnemonic('e');
        chooser.setCurrentDirectory(new File("picture"));
        chooser.setDialogTitle("Select Picture");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(
                new FileNameExtensionFilter("IMAGE file (*.jpg, *.jpeg, *.gif, *.png)",
                "jpg", "jpeg", "gif", "png"));
        chooser.setAccessory(new ImagePreviewer(chooser));
    }// </editor-fold>

    private GoodsMovieVO getVOInForm() {
        GoodsMovieVO goodsMovieVO = new GoodsMovieVO();
        goodsMovieVO.setActorVOs(getActorListFromString(txtActor.getText()));
        goodsMovieVO.setDateCreated(dtDateCreated.getSelectedDate().getTime());
        goodsMovieVO.setDirectorVO((DirectorVO) cbxDirector.getSelectedItem());
        goodsMovieVO.setMovCatVO((MovieCatgoryVO) cbxMovCat.getSelectedItem());
        goodsMovieVO.setMovDesc(txtMovDesc.getText());
        String lblMovIdText = lblMovId.getText();
        goodsMovieVO.setMovId(lblMovIdText.equals("")
                || lblMovIdText.equals("Thêm mới") ? null : Long.valueOf(lblMovIdText));
        goodsMovieVO.setMovName(txtMovName.getText());
        goodsMovieVO.setMovPicURL(currentPicURL);
        try {
            goodsMovieVO.setMovPrice(Double.valueOf(txtMovPrice.getText().trim()));
        } catch (Exception ex) {
            goodsMovieVO.setMovPrice(-1);
        }
        try {
            goodsMovieVO.setMovSaleOff(Double.valueOf(txtMovSaleOff.getText().trim()));
        } catch (Exception ex) {
            goodsMovieVO.setMovSaleOff(-1);
        }
        return goodsMovieVO;
    }

    private List<ActorVO> getActorListFromString(String actorString) {
        List<ActorVO> actorVOs = new ArrayList<ActorVO>();
        StringTokenizer actorToken = new StringTokenizer(actorString, ",");
        ActorVO actorVO;
        String actStr;
        while (actorToken.hasMoreElements()) {
            actStr = actorToken.nextToken();

            actorVO = new ActorVO();
            actorVO.setActorId(Long.valueOf(actStr.substring(
                    actStr.lastIndexOf("(") + 1, actStr.length() - 1)));
            actorVO.setActorName(actStr.substring(0, actStr.lastIndexOf("(") - 1));
            actorVOs.add(actorVO);
        }
        return actorVOs;
    }

    private String getActorStringFromList(List<ActorVO> actorVOs) {
        if (actorVOs == null) {
            return "";
        }
        StringBuilder actorStringBuilder = new StringBuilder();
        for (ActorVO actorVO : actorVOs) {
            actorStringBuilder.append(actorVO.getActorName());
            actorStringBuilder.append("(");
            actorStringBuilder.append(actorVO.getActorId());
            actorStringBuilder.append(")");
            actorStringBuilder.append(",");
        }
        int length = actorStringBuilder.length();
        if (length > 0) {
            actorStringBuilder.deleteCharAt(length - 1);
        }
        return actorStringBuilder.toString();
    }

    private List<Long> getSelectedIds() {
        List<Long> selectedIds = new ArrayList<Long>();
        for (int idx : tblGoodsMovie.getSelectedRows()) {
            selectedIds.add((Long) tblGoodsMovie.getValueAt(idx, 0));
        }
        return selectedIds;
    }

    private void prepareUpload() {
        uploadModel = new DataChangingModel();
        uploadModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onUploadResultRetrieved(evt);
            }
        });
    }

    private void dispatchUploadEvent(String fileName, byte[] fileUploaded) {
        btnUploadPic.setEnabled(false);

        UploadEvent uploadEvent = new UploadEvent(this);
        uploadEvent.setFileName(fileName);
        uploadEvent.setFileUploaded(fileUploaded);
        uploadEvent.setDataChangingModel(uploadModel);

        goodsIFrameListener.onUploadPerformed(uploadEvent);
    }

    private void onUploadResultRetrieved(ResultRetrievedEvent evt) {
        int status = uploadModel.getStatus();
        String errorMsg = checkError(status);
        if (errorMsg != null) {
            JOptionPane.showMessageDialog(this, errorMsg);
        } else {
            JOptionPane.showMessageDialog(this, "Thao tác thành công");
        }

        btnUploadPic.setEnabled(true);
    }
}
