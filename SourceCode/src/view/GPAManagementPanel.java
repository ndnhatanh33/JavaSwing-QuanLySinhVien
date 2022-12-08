/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.BangDiemDao;
import controller.DataValidator;
import controller.HocPhanDao;
import controller.SinhVienDao;
import java.awt.Color;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.BangDiem;
import model.HocPhan;
import model.MessageDialogHelper;
import model.SinhVien;

/**
 *
 * @author ndnha
 */
public class GPAManagementPanel extends javax.swing.JPanel {

    private MainForm parentForm;
    private DefaultTableModel tblModel;

    /**
     * Creates new form GPAManagementPanel
     */
    public GPAManagementPanel() {
        initComponents();

        initTable();
        initComboBox();
        lblDiemTB.setText("");
        lblXepLoai.setText("");
    }

    private void initTable() {
        tblModel = new DefaultTableModel();
        tblModel.setColumnIdentifiers(new String[]{
            "STT", "Mã học phần", "Tên học phần", "Năm học", "Học kỳ", "Điểm", "Điểm chữ"
        });
        tblGPA.setModel(tblModel);
    }

    private void initComboBox() {
        try {
            HocPhanDao dao = new HocPhanDao();
            ArrayList<HocPhan> list = dao.findAll();
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            for (HocPhan it : list) {
                model.addElement(it.getMSHP());
            }
            cbxMSHP.setModel(model);
            cbxMSHP.setSelectedIndex(-1);
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }

    private String diemchu(float diem) {
        //int diem = Integer.parseInt(num);
        if (diem >= 9) {
            return "A";
        } else if (diem >= 8) {
            return "B+";
        } else if (diem >= 7) {
            return "B";
        } else if (diem >= 6.5) {
            return "C+";
        } else if (diem >= 5.5) {
            return "C";
        } else if (diem >= 5) {
            return "D+";
        } else if (diem >= 4) {
            return "D";
        } else {
            return "F";
        }
    }

    private float dtb(int n, String diem[]) {
        float[] thang4 = new float[n];
        int i = 0;
        float tong = 0.0f;
        for (i = 0; i < n; i++) {
            if (diem[i].equals("A")) {
                thang4[i] = 4.0f;
            } else if (diem[i].equals("B+")) {
                thang4[i] = 3.5f;
            } else if (diem[i].equals("B")) {
                thang4[i] = 3.0f;
            } else if (diem[i].equals("C+")) {
                thang4[i] = 2.5f;
            } else if (diem[i].equals("C")) {
                thang4[i] = 2.0f;
            } else if (diem[i].equals("D+")) {
                thang4[i] = 1.5f;
            } else if (diem[i].equals("D")) {
                thang4[i] = 1.0f;
            } else if (diem[i].equals("F")) {
                thang4[i] = 0.0f;
            }
            tong += thang4[i];
        }
        return tong / n;
    }

    private String xeploai(float diem) {
        if (diem >= 3.6) {
            return "Xuất sắc";
        } else if (diem >= 3.2) {
            return "Giỏi";
        } else if (diem >= 2.5) {
            return "Khá";
        } else if (diem >= 2.0) {
            return "Trung bình";
        } else if (diem >= 1.0) {
            return "Trung bình yếu";
        } else {
            return "Kém";
        }
    }

    private void loadBangDiem(String mssv) {
        try {
            BangDiemDao dao = new BangDiemDao();
            ArrayList<BangDiem> list = dao.findByID(mssv);
            HocPhanDao dao1 = new HocPhanDao();
            tblModel.setRowCount(0);
            int stt = 0;
            String dsDiem[] = new String[50];
            for (BangDiem it : list) {
                dsDiem[stt] = new String(diemchu((float) it.getDiem()));
                String tenHP = dao1.findTenHP(it.getMSHP());
                tblModel.addRow(new Object[]{
                    ++stt, it.getMSHP(), tenHP, it.getNamHoc(), it.getHocKy(),
                    it.getDiem(), diemchu((float) it.getDiem())
                });
            }
            tblModel.fireTableDataChanged();
            if (stt != 0) {
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String dtb = decimalFormat.format(dtb(stt, dsDiem));
                lblDiemTB.setText(dtb);
                lblXepLoai.setText(xeploai(dtb(stt, dsDiem)));
            }

        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }

    private void loadBangDiemTheoNamHoc(String mssv, String namHoc) {
        try {
            BangDiemDao dao = new BangDiemDao();
            ArrayList<BangDiem> list = dao.findByID_NamHoc(mssv, namHoc);
            HocPhanDao dao1 = new HocPhanDao();
            tblModel.setRowCount(0);
            int stt = 0;
            String dsDiem[] = new String[50];
            for (BangDiem it : list) {
                dsDiem[stt] = new String(diemchu((float) it.getDiem()));
                String tenHP = dao1.findTenHP(it.getMSHP());
                tblModel.addRow(new Object[]{
                    ++stt, it.getMSHP(), tenHP, it.getNamHoc(), it.getHocKy(),
                    it.getDiem(), diemchu((float) it.getDiem())
                });
            }
            tblModel.fireTableDataChanged();
            if (stt != 0) {
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String dtb = decimalFormat.format(dtb(stt, dsDiem));
                lblDiemTB.setText(dtb);
                lblXepLoai.setText(xeploai(dtb(stt, dsDiem)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }

    private void loadBangDiemTheoHocKy(String mssv, String hocKy) {
        try {
            BangDiemDao dao = new BangDiemDao();
            ArrayList<BangDiem> list = dao.findByID_HocKy(mssv, hocKy);
            HocPhanDao dao1 = new HocPhanDao();
            tblModel.setRowCount(0);
            int stt = 0;
            String dsDiem[] = new String[50];
            for (BangDiem it : list) {
                dsDiem[stt] = new String(diemchu((float) it.getDiem()));
                String tenHP = dao1.findTenHP(it.getMSHP());
                tblModel.addRow(new Object[]{
                    ++stt, it.getMSHP(), tenHP, it.getNamHoc(), it.getHocKy(),
                    it.getDiem(), diemchu((float) it.getDiem())
                });
            }
            tblModel.fireTableDataChanged();
            if (stt != 0) {
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String dtb = decimalFormat.format(dtb(stt, dsDiem));
                lblDiemTB.setText(dtb);
                lblXepLoai.setText(xeploai(dtb(stt, dsDiem)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }

    private void loadBangDiem(String mssv, String namHoc, String hocKy) {
        try {
            BangDiemDao dao = new BangDiemDao();
            ArrayList<BangDiem> list = dao.findByID_NamHoc_HocKy(mssv, namHoc, hocKy);
            HocPhanDao dao1 = new HocPhanDao();
            tblModel.setRowCount(0);
            int stt = 0;
            String dsDiem[] = new String[50];
            for (BangDiem it : list) {
                dsDiem[stt] = new String(diemchu((float) it.getDiem()));
                String tenHP = dao1.findTenHP(it.getMSHP());
                tblModel.addRow(new Object[]{
                    ++stt, it.getMSHP(), tenHP, it.getNamHoc(), it.getHocKy(),
                    it.getDiem(), diemchu((float) it.getDiem())
                });
            }
            tblModel.fireTableDataChanged();
            if (stt != 0) {
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String dtb = decimalFormat.format(dtb(stt, dsDiem));
                lblDiemTB.setText(dtb);
                lblXepLoai.setText(xeploai(dtb(stt, dsDiem)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }

    private void inbangdiem() {
        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmpty(txtStudentID, sb, "Mã sinh viên cần phải nhập");
        DataValidator.validateEmpty(cbxNamHoc, sb, "Cần chọn năm học");
        DataValidator.validateEmpty(cbxHocKy, sb, "Cần chọn học kỳ");
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(parentForm, sb.toString(), "Lỗi");
            return;
        }
        try {
            String file = "C:/Users/ndnha/Desktop/inBangDiemJAVA.txt";
            PrintWriter out = new PrintWriter(file);
            SinhVienDao con = new SinhVienDao();
            SinhVien sv = con.findById(txtStudentID.getText());
            if (sv != null) {
                out.println("\t\t     Trường Đại Học Cần Thơ");
                out.println("\t\t        Bảng Điểm Học Kỳ");
                out.println("");
                out.println(String.format("Họ và tên: %s\t\tMã số sinh viên: %s", sv.getHoTen(), sv.getMaSinhVien()));
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String strDate = formatter.format(sv.getDob());
                out.println(String.format("Ngày sinh: %s", strDate));
                out.println(String.format("Email: %s", sv.getEmail()));
                out.println(String.format("Số điện thoại: %s", sv.getSoDT()));
                out.println(String.format("Địa chỉ: %s", sv.getDiaChi()));
                out.println("");
                String namhoc = (String) cbxNamHoc.getSelectedItem();
                String hocky = (String) cbxHocKy.getSelectedItem();
                out.println(String.format("Học Kỳ %s - Năm Học %s", hocky, namhoc));
                out.println(String.format("Mã HP\t\tTên học phần                     \tĐiểm số\tĐiểm chữ"));
                out.println("");

                BangDiemDao con1 = new BangDiemDao();
                ArrayList<BangDiem> list = con1.findByID_NamHoc_HocKy(txtStudentID.getText(), namhoc, hocky);
                HocPhanDao dao2 = new HocPhanDao();
                int stt = 0;
                String dsDiem[] = new String[50];
                for (BangDiem it : list) {
                    dsDiem[stt] = new String(diemchu((float) it.getDiem()));
                    stt++;
                    String tenHP = dao2.findTenHP(it.getMSHP());
                    out.println(String.format("%s\t%-33s\t%s\t%s",
                            it.getMSHP(), tenHP, it.getDiem(), diemchu((float) it.getDiem())));
                }
                if (stt != 0) {
                    DecimalFormat decimalFormat = new DecimalFormat("#.00");
                    String dtb = decimalFormat.format(dtb(stt, dsDiem));
                    out.println(String.format("\nĐiểm trung bình: %s\t\t Xếp loại: %s\n", dtb, xeploai(dtb(stt, dsDiem))));
                }
                int day = Calendar.getInstance().get(Calendar.DATE);
                int month = Calendar.getInstance().get(Calendar.MONTH);
                int year = Calendar.getInstance().get(Calendar.YEAR);
                out.println("");
                out.println(String.format("Ghi chú \nĐiểm trung bình được phân loại như sau:\n"
                        + "        Loại Xuất sắc	từ 3.6 đến 4.00	Loại Khá	từ 2.5 đến 3.19\n"
                        + "        Loại Giỏi	từ 3.2 đến 3.59	Loại Trung bình	từ 2.0 đến 2.49\n\n"
                        + "                                     Cần Thơ, Ngày %d Tháng %d Năm %d", day, month + 1, year));
                out.close();
                JOptionPane.showMessageDialog(parentForm, "Xuất bản điểm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parentForm, "Dữ liệu không tồn tại", "Lỗi!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnSearch = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtDiem = new javax.swing.JTextField();
        cbxNamHoc = new javax.swing.JComboBox<>();
        cbxHocKy = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cbxMSHP = new javax.swing.JComboBox<>();
        btnNew = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblDiemTB = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblXepLoai = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGPA = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtStudentID = new javax.swing.JTextField();
        btnInBangDiem = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("QUẢN LÝ ĐIỂM");

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/search-icon-16.png"))); // NOI18N
        btnSearch.setText("Tìm kiếm");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jLabel4.setText("Họ Tên:");

        jLabel5.setText("Năm học:");

        jLabel6.setText("Học kỳ:");

        jLabel7.setText("Điểm:");

        cbxNamHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<none>", "2019-2020", "2020-2021", "2021-2022", "2022-2023" }));

        cbxHocKy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<none>", "1", "2", "3" }));

        jLabel9.setText("Mã số học phần:");

        cbxMSHP.setEditable(true);

        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/new-icon-16.png"))); // NOI18N
        btnNew.setText("Làm mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cbxNamHoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbxHocKy, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbxMSHP, 0, 170, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNew)))
                        .addGap(16, 16, 16))
                    .addComponent(txtName)))
            .addComponent(jSeparator2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbxNamHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbxHocKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbxMSHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNew))
                .addContainerGap())
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Điểm TB");

        lblDiemTB.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblDiemTB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Xếp loại");

        lblXepLoai.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblXepLoai.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblDiemTB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
                .addGap(57, 57, 57)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblXepLoai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDiemTB)
                    .addComponent(lblXepLoai))
                .addContainerGap())
        );

        tblGPA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblGPA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGPAMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGPA);

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Save-icon.png"))); // NOI18N
        btnSave.setText("Lưu");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Actions-edit-delete-icon-16.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Actions-document-edit-icon-16.png"))); // NOI18N
        btnUpdate.setText("Cập nhật");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(btnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDelete)
                .addGap(5, 5, 5))
        );

        jLabel3.setText("Mã số sinh viên:");

        btnInBangDiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/print-16.png"))); // NOI18N
        btnInBangDiem.setText("In bảng điểm");
        btnInBangDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInBangDiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtStudentID, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(btnSearch))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(41, 41, 41)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnInBangDiem)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtStudentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnInBangDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        txtName.setText("");
        txtStudentID.setText("");
        txtDiem.setText("");
        cbxHocKy.setSelectedIndex(0);
        cbxMSHP.setSelectedIndex(-1);
        cbxNamHoc.setSelectedIndex(0);
        tblModel.setRowCount(0);
        lblDiemTB.setText("");
        lblXepLoai.setText("");
        txtName.setBackground(Color.white);
        txtStudentID.setBackground(Color.white);
        txtDiem.setBackground(Color.white);
        cbxHocKy.setBackground(Color.white);
        cbxMSHP.setBackground(Color.white);
        cbxNamHoc.setBackground(Color.white);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmpty(txtStudentID, sb, "Mã sinh viên cần phải nhập");
        DataValidator.validateEmpty(cbxNamHoc, sb, "Cần chọn năm học");
        DataValidator.validateEmpty(cbxHocKy, sb, "Cần chọn học kỳ");
        DataValidator.validateEmpty(cbxMSHP, sb, "Cần nhập mã số học phần");
        DataValidator.validateEmpty(txtDiem, sb, "Điểm cần phải nhập");
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(parentForm, sb.toString(), "Lỗi");
            return;
        }
        try {
            BangDiem bd = new BangDiem();
            bd.setMaSinhVien(txtStudentID.getText());
            bd.setNamHoc((String) cbxNamHoc.getSelectedItem());
            bd.setHocKy((String) cbxHocKy.getSelectedItem());
            bd.setMSHP((String) cbxMSHP.getSelectedItem());
            bd.setDiem(Double.parseDouble(txtDiem.getText()));

            BangDiemDao dao = new BangDiemDao();
            if (dao.insert(bd)) {
                MessageDialogHelper.showMessageDialog(parentForm, "Bảng điểm đã được lưu", "Thông báo");
                loadBangDiem(txtStudentID.getText());
            } else {
                MessageDialogHelper.showMessageDialog(parentForm, "Không thể lưu bảng điểm", "Thông báo");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmpty(txtStudentID, sb, "Mã sinh viên cần phải nhập");
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(parentForm, sb.toString(), "Lỗi");
            return;
        }
        try {
            SinhVienDao dao = new SinhVienDao();
            SinhVien sv = dao.findById(txtStudentID.getText());

            if (sv != null) {
                txtName.setText(sv.getHoTen());
                if (cbxNamHoc.getSelectedIndex() == 0 && cbxHocKy.getSelectedIndex() == 0) {
                    loadBangDiem(txtStudentID.getText());
                } else if (cbxNamHoc.getSelectedIndex() != 0 && cbxHocKy.getSelectedIndex() == 0) {
                    loadBangDiemTheoNamHoc(txtStudentID.getText(), (String) cbxNamHoc.getSelectedItem());
                } else if (cbxNamHoc.getSelectedIndex() == 0 && cbxHocKy.getSelectedIndex() != 0) {
                    loadBangDiemTheoHocKy(txtStudentID.getText(), (String) cbxHocKy.getSelectedItem());
                } else {
                    loadBangDiem(txtStudentID.getText(), (String) cbxNamHoc.getSelectedItem(), (String) cbxHocKy.getSelectedItem());
                }
            } else {
                MessageDialogHelper.showMessageDialog(parentForm, "Không tìm thấy sinh viên có mã theo yêu cầu", "Thông báo");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmpty(txtStudentID, sb, "Mã sinh viên cần phải nhập");
        DataValidator.validateEmpty(cbxNamHoc, sb, "Cần chọn năm học");
        DataValidator.validateEmpty(cbxHocKy, sb, "Cần chọn học kỳ");
        DataValidator.validateEmpty(cbxMSHP, sb, "Cần nhập mã số học phần");
        DataValidator.validateEmpty(txtDiem, sb, "Điểm cần phải nhập");
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(parentForm, sb.toString(), "Lỗi");
            return;
        }
        if (MessageDialogHelper.showConfirmDialog(parentForm,
                "Bạn có muốn cập nhật bảng điểm không?", "Hỏi") == JOptionPane.NO_OPTION) {
            return;
        }
        try {
            BangDiem bd = new BangDiem();
            bd.setMaSinhVien(txtStudentID.getText());
            bd.setNamHoc((String) cbxNamHoc.getSelectedItem());
            bd.setHocKy((String) cbxHocKy.getSelectedItem());
            bd.setMSHP((String) cbxMSHP.getSelectedItem());
            bd.setDiem(Double.parseDouble(txtDiem.getText()));

            BangDiemDao dao = new BangDiemDao();
            if (dao.update(bd)) {
                MessageDialogHelper.showMessageDialog(parentForm, "Bảng điểm đã được cập nhật", "Thông báo");
                loadBangDiem(txtStudentID.getText());
            } else {
                MessageDialogHelper.showMessageDialog(parentForm, "Không thể cập nhật bảng điểm", "Thông báo");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        StringBuilder sb = new StringBuilder();
        DataValidator.validateEmpty(txtStudentID, sb, "Mã sinh viên cần phải nhập");
        DataValidator.validateEmpty(cbxNamHoc, sb, "Cần chọn năm học");
        DataValidator.validateEmpty(cbxHocKy, sb, "Cần chọn học kỳ");
        DataValidator.validateEmpty(cbxMSHP, sb, "Cần nhập mã số học phần");
        if (sb.length() > 0) {
            MessageDialogHelper.showErrorDialog(parentForm, sb.toString(), "Lỗi");
            return;
        }
        if (MessageDialogHelper.showConfirmDialog(parentForm,
                "Bạn có muốn xóa dữ liệu trong bảng điểm không?", "Hỏi") == JOptionPane.NO_OPTION) {
            return;
        }
        try {
            BangDiemDao dao = new BangDiemDao();
            if (dao.delete(txtStudentID.getText(), (String) cbxNamHoc.getSelectedItem(),
                    (String) cbxHocKy.getSelectedItem(), (String) cbxMSHP.getSelectedItem())) {
                MessageDialogHelper.showMessageDialog(parentForm, "Dữ liệu trong bảng điểm đã được xóa", "Thông báo");
                loadBangDiem(txtStudentID.getText());
                btnNewActionPerformed(evt);
            } else {
                MessageDialogHelper.showMessageDialog(parentForm, "Không thể xóa dữ liệu trong bảng điểm", "Thông báo");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void tblGPAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGPAMouseClicked
        try {
            int row = tblGPA.getSelectedRow();

            if (row >= 0) {
                String mshp = (String) tblGPA.getValueAt(row, 1);
                String id = txtStudentID.getText();
                String nam = (String) tblGPA.getValueAt(row, 3);
                String hocky = (String) tblGPA.getValueAt(row, 4);
                BangDiemDao dao = new BangDiemDao();
                BangDiem bd = dao.findByID_MSHP_NamHoc_HocKy(id, mshp, nam, hocky);

                if (bd != null) {
                    txtDiem.setText(String.valueOf(bd.getDiem()));
                    cbxNamHoc.setSelectedItem(bd.getNamHoc());
                    cbxHocKy.setSelectedItem(bd.getHocKy());
                    cbxMSHP.setSelectedItem(bd.getMSHP());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageDialogHelper.showErrorDialog(parentForm, e.getMessage(), "Lỗi");
        }
    }//GEN-LAST:event_tblGPAMouseClicked

    private void btnInBangDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInBangDiemActionPerformed
        inbangdiem();
    }//GEN-LAST:event_btnInBangDiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInBangDiem;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbxHocKy;
    private javax.swing.JComboBox<String> cbxMSHP;
    private javax.swing.JComboBox<String> cbxNamHoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblDiemTB;
    private javax.swing.JLabel lblXepLoai;
    private javax.swing.JTable tblGPA;
    private javax.swing.JTextField txtDiem;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtStudentID;
    // End of variables declaration//GEN-END:variables
}
