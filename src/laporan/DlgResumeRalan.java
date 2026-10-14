/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */
package laporan;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import rekammedis.RMDataResumePasien;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgKabupaten;
import simrskhanza.DlgKecamatan;
import simrskhanza.DlgKelurahan;
import simrskhanza.DlgCariCaraBayar;

/**
 *
 * @author perpustakaan
 */
public final class DlgResumeRalan extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, ps2;
    private ResultSet rs, rs2;
    private DlgCariPoli poli = new DlgCariPoli(null, false);
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private DlgKabupaten kabupaten = new DlgKabupaten(null, false);
    private DlgKecamatan kecamatan = new DlgKecamatan(null, false);
    private DlgKelurahan kelurahan = new DlgKelurahan(null, false);
    private DlgCariCaraBayar penjab = new DlgCariCaraBayar(null, false);
    private int i = 0, lama = 0, baru = 0, laki = 0, per = 0;
    private String setbaru = "", setlama = "", umurlk = "", umurpr = "", kddiangnosa = "", diagnosa = "", pilihan = "", status = "", order = "ORDER BY reg_periksa.tgl_registrasi DESC";
    private StringBuilder htmlContent;

    /**
     * Creates new form DlgLhtBiaya
     *
     * @param parent
     * @param modal
     */
    public DlgResumeRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);
        setSize(885, 674);

        tabMode = new DefaultTableModel(null, new Object[]{"No", "No_rawat", "No.Rm", "Nama Pasien", "Tanggal_regis", "Nama Dokter", "Nama Poli", "Status"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        table1.setModel(tabMode);
        table1.setPreferredScrollableViewportSize(new Dimension(500, 500));
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = table1.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(25);
            } else if (i == 1) {
                column.setPreferredWidth(110);
            } else if (i == 2) {
                column.setPreferredWidth(60);
            } else if (i == 3) {
                column.setPreferredWidth(200);
            } else if (i == 4) {
                column.setPreferredWidth(90);
            } else if (i == 5) {
                column.setPreferredWidth(200);
            } else if (i == 6) {
                column.setPreferredWidth(200);
            } else if (i == 7) {
                column.setPreferredWidth(200);
            }
        }
        table1.setDefaultRenderer(Object.class, new WarnaTable());
        TCari.setDocument(new batasInput((int) 90).getKata(TCari));
        if (koneksiDB.CARICEPAT().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }

        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (poli.getTable().getSelectedRow() != -1) {
                    kdpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 0).toString());
                    nmpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 1).toString());
                }
                kdpoli.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                poli.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (penjab.getTable().getSelectedRow() != -1) {
                    kdpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(), 1).toString());
                    nmpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(), 2).toString());
                }
                kdpenjab.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                penjab.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    penjab.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        kabupaten.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    kabupaten.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        kelurahan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    kelurahan.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    kddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    nmdokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                }
                kddokter.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                dokter.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        dokter.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    dokter.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        ChkInput.setSelected(false);
        isForm();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TKd = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppInputResume = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelGlass5 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        jLabel8 = new widget.Label();
        cmbStatusBayar = new widget.ComboBox();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label17 = new widget.Label();
        kdpoli = new widget.TextBox();
        nmpoli = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        label19 = new widget.Label();
        kdpenjab = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        label20 = new widget.Label();
        kddokter = new widget.TextBox();
        nmdokter = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        TNoRw = new widget.TextBox();
        label21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        label22 = new widget.Label();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        table1 = new widget.Table();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppInputResume.setBackground(new java.awt.Color(255, 255, 254));
        ppInputResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppInputResume.setForeground(java.awt.Color.darkGray);
        ppInputResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppInputResume.setText("Input Resume Pasien");
        ppInputResume.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppInputResume.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppInputResume.setName("ppInputResume"); // NOI18N
        ppInputResume.setPreferredSize(new java.awt.Dimension(175, 25));
        ppInputResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppInputResumeBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppInputResume);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ PASIEN RALAN YANG BELUM ADA RESUME ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass5.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass5.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass5.add(Tgl2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass5.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(155, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass5.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnCari);

        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass5.add(jLabel7);

        jLabel8.setText("Urutkan :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass5.add(jLabel8);

        cmbStatusBayar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-Silahkan Pilih-", "Dokter", "Poli" }));
        cmbStatusBayar.setName("cmbStatusBayar"); // NOI18N
        cmbStatusBayar.setPreferredSize(new java.awt.Dimension(100, 23));
        cmbStatusBayar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbStatusBayarItemStateChanged(evt);
            }
        });
        panelGlass5.add(cmbStatusBayar);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnPrint);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnKeluar);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Filter Data");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 104));
        FormInput.setLayout(null);

        label17.setText("Unit/Poli :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label17);
        label17.setBounds(0, 10, 75, 23);

        kdpoli.setEditable(false);
        kdpoli.setName("kdpoli"); // NOI18N
        kdpoli.setPreferredSize(new java.awt.Dimension(75, 23));
        kdpoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpoliKeyPressed(evt);
            }
        });
        FormInput.add(kdpoli);
        kdpoli.setBounds(78, 10, 85, 23);

        nmpoli.setEditable(false);
        nmpoli.setName("nmpoli"); // NOI18N
        nmpoli.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmpoli);
        nmpoli.setBounds(165, 10, 228, 23);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("Alt+3");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        BtnSeek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek2KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek2);
        BtnSeek2.setBounds(396, 10, 28, 23);

        label19.setText("Tgl Rawat :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label19);
        label19.setBounds(430, 40, 75, 23);

        kdpenjab.setEditable(false);
        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.setPreferredSize(new java.awt.Dimension(75, 23));
        kdpenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenjabKeyPressed(evt);
            }
        });
        FormInput.add(kdpenjab);
        kdpenjab.setBounds(78, 70, 85, 23);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        nmpenjab.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmpenjab);
        nmpenjab.setBounds(165, 70, 228, 23);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('3');
        BtnSeek3.setToolTipText("Alt+3");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        BtnSeek3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek3KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek3);
        BtnSeek3.setBounds(396, 70, 28, 23);

        label20.setText("Dokter :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label20);
        label20.setBounds(0, 40, 75, 23);

        kddokter.setName("kddokter"); // NOI18N
        kddokter.setPreferredSize(new java.awt.Dimension(75, 23));
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        FormInput.add(kddokter);
        kddokter.setBounds(78, 40, 85, 23);

        nmdokter.setName("nmdokter"); // NOI18N
        nmdokter.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmdokter);
        nmdokter.setBounds(165, 40, 228, 23);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('3');
        BtnSeek4.setToolTipText("Alt+3");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        BtnSeek4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek4KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek4);
        BtnSeek4.setBounds(396, 40, 28, 23);

        TNoRw.setEditable(false);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(TNoRw);
        TNoRw.setBounds(515, 70, 140, 23);

        label21.setText("Cara Bayar :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label21);
        label21.setBounds(0, 70, 75, 23);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2025" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        FormInput.add(DTPCari2);
        DTPCari2.setBounds(520, 40, 130, 23);

        label22.setText("No Rawat :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(100, 23));
        FormInput.add(label22);
        label22.setBounds(430, 70, 75, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        table1.setComponentPopupMenu(jPopupMenu1);
        table1.setName("table1"); // NOI18N
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        table1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                table1KeyPressed(evt);
            }
        });
        Scroll.setViewportView(table1);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("List Pasien yang belum ada resume", internalFrame2);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        /*this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            File g = new File("file2.css");
            BufferedWriter bg = new BufferedWriter(new FileWriter(g));
            bg.write(
                    ".isi td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                    + ".isi2 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"
                    + ".isi3 td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                    + ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
            );
            bg.close();

            File f;
            BufferedWriter bw;

            pilihan = (String) JOptionPane.showInputDialog(null, "Silahkan pilih laporan..!", "Pilihan Cetak", JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Laporan 1 (HTML)", "Laporan 2 (WPS)", "Laporan 3 (CSV)", "Laporan 4 (Jasper)"}, "Laporan 1 (HTML)");
            switch (pilihan) {
                case "Laporan 1 (HTML)":
                    htmlContent = new StringBuilder();
                    htmlContent.append(
                            "<tr class='isi'>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lama</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Baru</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>L</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>P</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alamat</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diagnosa</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dokter Jaga</b></td>"
                            + "</tr>"
                    );
                    if (TabRawat.getSelectedIndex() == 0) {
                        for (i = 0; i < tabMode.getRowCount(); i++) {
                            htmlContent.append(
                                    "<tr class='isi'>"
                                    + "<td valign='top'>" + tabMode.getValueAt(i, 0) + "</td>"
                                    + "<td valign='top' align='center'>" + tabMode.getValueAt(i, 1) + "</td>"
                                    + "<td valign='top' align='center'>" + tabMode.getValueAt(i, 2) + "</td>"
                                    + "<td valign='top'>" + tabMode.getValueAt(i, 3) + "</td>"
                                    + "<td valign='top' align='center'>" + tabMode.getValueAt(i, 4) + "</td>"
                                    + "<td valign='top' align='center'>" + tabMode.getValueAt(i, 5) + "</td>"
                                    + "<td valign='top'>" + tabMode.getValueAt(i, 6) + "</td>"
                                    + "<td valign='top' align='center'>" + tabMode.getValueAt(i, 7) + "</td>"
                                    + "<td valign='top'>" + tabMode.getValueAt(i, 8) + "</td>"
                                    + "<td valign='top'>" + tabMode.getValueAt(i, 9) + "</td>"
                                    + "</tr>"
                            );
                        }
                    } else {

                    }

                    f = new File("KunjunganRalan.html");
                    bw = new BufferedWriter(new FileWriter(f));
                    bw.write("<html>"
                            + "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"
                            + "<body>"
                            + "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                            + "<tr class='isi2'>"
                            + "<td valign='top' align='center'>"
                            + "<font size='4' face='Tahoma'>" + akses.getnamars() + "</font><br>"
                            + akses.getalamatrs() + ", " + akses.getkabupatenrs() + ", " + akses.getpropinsirs() + "<br>"
                            + akses.getkontakrs() + ", E-mail : " + akses.getemailrs() + "<br><br>"
                            + "<font size='2' face='Tahoma'>LAPORAN KUNJUNGAN RAWAT JALAN PERIODE " + Tgl1.getSelectedItem() + " s.d. " + Tgl2.getSelectedItem() + "<br><br></font>"
                            + "</td>"
                            + "</tr>"
                            + "</table>"
                            + "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                            + htmlContent.toString()
                            + "</table>"
                            + "</body>"
                            + "</html>"
                    );

                    bw.close();
                    Desktop.getDesktop().browse(f.toURI());
                    break;
                case "Laporan 2 (WPS)":
                    htmlContent = new StringBuilder();
                    htmlContent.append(
                            "<tr class='isi'>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Lama</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Baru</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>L</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>P</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Alamat</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Diagnosa</b></td>"
                            + "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dokter Jaga</b></td>"
                            + "</tr>"
                    );
                    if (TabRawat.getSelectedIndex() == 0) {
                        for (i = 0; i < tabMode.getRowCount(); i++) {
                            htmlContent.append(
                                    "<tr class='isi'>"
                                    + "<td valign='top'>" + tabMode.getValueAt(i, 0) + "</td>"
                                    + "<td valign='top' align='center'>" + tabMode.getValueAt(i, 1) + "</td>"
                                    + "<td valign='top' align='center'>" + tabMode.getValueAt(i, 2) + "</td>"
                                    + "<td valign='top'>" + tabMode.getValueAt(i, 3) + "</td>"
                                    + "<td valign='top' align='center'>" + tabMode.getValueAt(i, 4) + "</td>"
                                    + "<td valign='top' align='center'>" + tabMode.getValueAt(i, 5) + "</td>"
                                    + "<td valign='top'>" + tabMode.getValueAt(i, 6) + "</td>"
                                    + "<td valign='top' align='center'>" + tabMode.getValueAt(i, 7) + "</td>"
                                    + "<td valign='top'>" + tabMode.getValueAt(i, 8) + "</td>"
                                    + "<td valign='top'>" + tabMode.getValueAt(i, 9) + "</td>"
                                    + "</tr>"
                            );
                        }
                    } else {

                    }
                    f = new File("KunjunganRalan.wps");
                    bw = new BufferedWriter(new FileWriter(f));
                    bw.write("<html>"
                            + "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"
                            + "<body>"
                            + "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                            + "<tr class='isi2'>"
                            + "<td valign='top' align='center'>"
                            + "<font size='4' face='Tahoma'>" + akses.getnamars() + "</font><br>"
                            + akses.getalamatrs() + ", " + akses.getkabupatenrs() + ", " + akses.getpropinsirs() + "<br>"
                            + akses.getkontakrs() + ", E-mail : " + akses.getemailrs() + "<br><br>"
                            + "<font size='2' face='Tahoma'>LAPORAN KUNJUNGAN RAWAT JALAN PERIODE " + Tgl1.getSelectedItem() + " s.d. " + Tgl2.getSelectedItem() + "<br><br></font>"
                            + "</td>"
                            + "</tr>"
                            + "</table>"
                            + "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                            + htmlContent.toString()
                            + "</table>"
                            + "</body>"
                            + "</html>"
                    );

                    bw.close();
                    Desktop.getDesktop().browse(f.toURI());
                    break;
                case "Laporan 3 (CSV)":
                    htmlContent = new StringBuilder();
                    htmlContent.append(
                            "\"No.\";\"Lama\";\"Baru\";\"Nama Pasien\";\"L\";\"P\";\"Alamat\";\"Kode\";\"Diagnosa\";\"Dokter Jaga\"\n"
                    );
                    if (TabRawat.getSelectedIndex() == 0) {
                        for (i = 0; i < tabMode.getRowCount(); i++) {
                            htmlContent.append(
                                    "\"" + tabMode.getValueAt(i, 0) + "\";\"" + tabMode.getValueAt(i, 1) + "\";\"" + tabMode.getValueAt(i, 2) + "\";\"" + tabMode.getValueAt(i, 3) + "\";\"" + tabMode.getValueAt(i, 4) + "\";\"" + tabMode.getValueAt(i, 5) + "\";\"" + tabMode.getValueAt(i, 6) + "\";\"" + tabMode.getValueAt(i, 7) + "\";\"" + tabMode.getValueAt(i, 8) + "\";\"" + tabMode.getValueAt(i, 9) + "\"\n"
                            );
                        }
                    } else {

                    }

                    f = new File("KunjunganRalan.csv");
                    bw = new BufferedWriter(new FileWriter(f));
                    bw.write(htmlContent.toString());

                    bw.close();
                    Desktop.getDesktop().browse(f.toURI());
                    break;
                case "Laporan 4 (Jasper)":
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", akses.getnamars());
                    param.put("alamatrs", akses.getalamatrs());
                    param.put("kotars", akses.getkabupatenrs());
                    param.put("propinsirs", akses.getpropinsirs());
                    param.put("kontakrs", akses.getkontakrs());
                    param.put("emailrs", akses.getemailrs());
                    param.put("periode", Tgl1.getSelectedItem() + " s.d. " + Tgl2.getSelectedItem());
                    param.put("lama", lama);
                    param.put("baru", baru);
                    param.put("total", (lama + baru));
                    param.put("laki", laki);
                    param.put("perempuan", per);
                    param.put("tanggal", Tgl2.getDate());
                    Sequel.queryu("delete from temporary where temp37='" + akses.getalamatip() + "'");
                    if (TabRawat.getSelectedIndex() == 0) {
                        if (tabMode.getRowCount() > 0) {
                            for (int r = 0; r < tabMode.getRowCount(); r++) {
                                if (!table1.getValueAt(r, 0).toString().contains(">>")) {
                                    Sequel.menyimpan("temporary", "'" + r + "','"
                                            + tabMode.getValueAt(r, 0).toString() + "','"
                                            + tabMode.getValueAt(r, 1).toString() + "','"
                                            + tabMode.getValueAt(r, 2).toString() + "','"
                                            + tabMode.getValueAt(r, 3).toString() + "','"
                                            + tabMode.getValueAt(r, 4).toString() + "','"
                                            + tabMode.getValueAt(r, 5).toString() + "','"
                                            + tabMode.getValueAt(r, 6).toString() + "','"
                                            + tabMode.getValueAt(r, 7).toString() + "','"
                                            + tabMode.getValueAt(r, 8).toString() + "','"
                                            + tabMode.getValueAt(r, 9).toString() + "','','','','','','','','','','','','','','','','','','','','','','','','','','','" + akses.getalamatip() + "'", "Rekap Nota Pembayaran");
                                }
                            }
                            Valid.MyReportqry("rptKunjunganRalan.jasper", "report", "::[ Laporan Kunjungan Rawat Jalan ]::", "select * from temporary where temporary.temp37='" + akses.getalamatip() + "' order by temporary.no", param);
                        } else {
                            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                            BtnPrint.requestFocus();
                        }
                    } else if (TabRawat.getSelectedIndex() == 1) {

                    }
                    break;
            }
        } catch (Exception e) {
        }
        this.setCursor(Cursor.getDefaultCursor());*/
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            //Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnKeluar, TKd);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
    tampil();
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil();
        this.setCursor(Cursor.getDefaultCursor());
    } else {
        Valid.pindah(evt, TKd, BtnPrint);
    }
}//GEN-LAST:event_BtnCariKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void kdpoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpoliKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select poliklinik.nm_poli from poliklinik where poliklinik.kd_poli=?", nmpoli, kdpoli.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //BtnAll.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Tgl2.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kdpoliKeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setAlwaysOnTop(false);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void kdpenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpenjabKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //BtnAll.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Tgl2.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kdpenjabKeyPressed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek3KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek3KeyPressed

    private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kddokterKeyPressed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek4KeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void ppInputResumeBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppInputResumeBtnPrintActionPerformed
        if (table1.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        } else if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            table1.requestFocus();
        } else {
            if (table1.getSelectedRow() != -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMDataResumePasien resume = new RMDataResumePasien(null, false);
                resume.isCek();
                resume.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setNoRm(TNoRw.getText(), DTPCari2.getDate());
                resume.tampil();
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppInputResumeBtnPrintActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked

        tampil();

    }//GEN-LAST:event_TabRawatMouseClicked

    private void cmbStatusBayarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbStatusBayarItemStateChanged
        if (cmbStatusBayar.getSelectedIndex() == 0) {
            tampil();
        } else if (cmbStatusBayar.getSelectedIndex() == 1) {
            order = "ORDER BY dokter.kd_dokter DESC";
            tampil();
        } else if (cmbStatusBayar.getSelectedIndex() == 2) {
            order = "ORDER BY poliklinik.kd_poli DESC";
            tampil();
        }
    }//GEN-LAST:event_cmbStatusBayarItemStateChanged

    private void table1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table1KeyPressed
        getData();
    }//GEN-LAST:event_table1KeyPressed

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
        getData();
    }//GEN-LAST:event_table1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgResumeRalan dialog = new DlgResumeRalan(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari2;
    private widget.panelisi FormInput;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.TextBox TNoRw;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.ComboBox cmbStatusBayar;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kddokter;
    private widget.TextBox kdpenjab;
    private widget.TextBox kdpoli;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.TextBox nmdokter;
    private widget.TextBox nmpenjab;
    private widget.TextBox nmpoli;
    private widget.panelisi panelGlass5;
    private javax.swing.JMenuItem ppInputResume;
    private widget.Table table1;
    // End of variables declaration//GEN-END:variables
public void tampil() {
        if (akses.getkode().equals("Admin Utama") || (akses.getkode().startsWith("SK"))) {
            try {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Valid.tabelKosong(tabMode);
                String sql = "SELECT reg_periksa.no_rawat,reg_periksa.no_rkm_medis, pasien.nm_pasien, reg_periksa.tgl_registrasi, "
                        + "dokter.nm_dokter, poliklinik.nm_poli, reg_periksa.status_lanjut FROM reg_periksa INNER JOIN dokter ON reg_periksa.kd_dokter = dokter.kd_dokter "
                        + "INNER JOIN poliklinik ON reg_periksa.kd_poli = poliklinik.kd_poli INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis LEFT JOIN resume_pasien ON reg_periksa.no_rawat = resume_pasien.no_rawat WHERE reg_periksa.tgl_registrasi BETWEEN ? AND ? "
                        + "AND reg_periksa.status_lanjut = 'Ralan' AND reg_periksa.stts <> 'Batal' AND poliklinik.kd_poli NOT IN ('P020', 'P021') "
                        + "AND resume_pasien.no_rawat IS NULL AND (dokter.nm_dokter like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ?)"
                        + "" + order + "";
                try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
                    ps.setString(1, Valid.SetTgl(Tgl1.getSelectedItem().toString()));
                    ps.setString(2, Valid.SetTgl(Tgl2.getSelectedItem().toString()));
                    ps.setString(3, "%" + TCari.getText().trim() + "%");
                    ps.setString(4, "%" + TCari.getText().trim() + "%");
                    ps.setString(5, "%" + TCari.getText().trim() + "%");
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            tabMode.addRow(new Object[]{
                                false, rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("tgl_registrasi"),
                                rs.getString("nm_dokter"), rs.getString("nm_poli"), rs.getString("status_lanjut")
                            });
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e.getMessage());
            }
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            try {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Valid.tabelKosong(tabMode);
                String sql = "SELECT reg_periksa.no_rawat,reg_periksa.no_rkm_medis, pasien.nm_pasien, reg_periksa.tgl_registrasi, "
                        + "dokter.nm_dokter, poliklinik.nm_poli, reg_periksa.status_lanjut "
                        + "FROM reg_periksa INNER JOIN dokter ON reg_periksa.kd_dokter = dokter.kd_dokter "
                        + "INNER JOIN poliklinik ON reg_periksa.kd_poli = poliklinik.kd_poli "
                        + "INNER JOIN pasien ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                        + "LEFT JOIN resume_pasien ON reg_periksa.no_rawat = resume_pasien.no_rawat "
                        + "WHERE reg_periksa.tgl_registrasi BETWEEN ? AND ? "
                        + "AND reg_periksa.status_lanjut = 'Ralan' AND reg_periksa.stts <> 'Batal' "
                        + "AND poliklinik.kd_poli NOT IN ('P020', 'P021') "
                        + "AND resume_pasien.no_rawat IS NULL "
                        + "AND dokter.kd_dokter = ? ORDER BY reg_periksa.tgl_registrasi DESC";
                try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
                    ps.setString(1, Valid.SetTgl(Tgl1.getSelectedItem().toString()));
                    ps.setString(2, Valid.SetTgl(Tgl2.getSelectedItem().toString()));
                    ps.setString(3, kddokter.getText().trim());
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            tabMode.addRow(new Object[]{
                                false, rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("tgl_registrasi"),
                                rs.getString("nm_dokter"), rs.getString("nm_poli"), rs.getString("status_lanjut")
                            });
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e.getMessage());
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
        jLabel7.setText("" + tabMode.getRowCount());
    }

    /*private void getData() {
        int row = table1.getSelectedRow();
        if (row != -1) {
            TKd.setText(tabMode.getValueAt(row, 0).toString());
        }
    }*/
    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 126));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    public void isCek() {
        BtnSeek4.setEnabled(false);
        BtnSeek2.setEnabled(false);
        BtnSeek3.setEnabled(false);
    }

    public void setNoRm(String kd_poli, String nm_poli, String kd_dokter, String nm_dokter) {
        kdpoli.setText(kd_poli);
        nmpoli.setText(nm_poli);
        kddokter.setText(akses.getkode());
        nmdokter.setText(Sequel.cariIsi("select nama from pegawai where nik=?", akses.getkode()));
    }

    private void getData() {
        if (table1.getSelectedRow() != -1) {
            TNoRw.setText(table1.getValueAt(table1.getSelectedRow(), 1).toString());
        }
    }
}
