package controlador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import vistas.*;
import modelo.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class controlador implements ActionListener {

    DefaultTableModel tdatos = new DefaultTableModel();
    DefaultTableModel cdatos = new DefaultTableModel();

    private modelo modeloModelo;
    private login vistaLogin;
    private dash vistaDash;
    private menuAdmin vistaMenuAdmin;
    private agregarProductos vistaAgregarProductos;
    private mydash vistamydash;
    private verProductos vistaVerProductos;
    private modificarProducto vistaModificarProducto;
    private agregarMarca vistaAgregarMarca;
    private modificarMarca vistaModificarMarca;
    private verMarcas vistaVerMarcas;
    private agregarUsuario vistaAgregarUsuario;
    private verUsuarios vistaVerUsuarios;
    private modificarUsuario vistaModificarUsuario;
    private nuevaVenta vistaNuevaVenta;

    private boolean cargaContent = false;
    private int contentInt = 1;

    public controlador() {
        modelo modelo = new modelo();
        login login = new login();
        dash dash = new dash();
        menuAdmin menuAdmin = new menuAdmin();
        mydash mydash = new mydash();
        agregarProductos agregarProductos = new agregarProductos();
        verProductos verProductos = new verProductos();
        modificarProducto modificarProducto = new modificarProducto();
        agregarMarca agregarMarca = new agregarMarca();
        modificarMarca modificarMarca = new modificarMarca();
        verMarcas verMarcas = new verMarcas();
        agregarUsuario agregarUsuario = new agregarUsuario();
        verUsuarios verUsuarios = new verUsuarios();
        modificarUsuario modificarUsuario = new modificarUsuario();
        nuevaVenta nuevaVenta = new nuevaVenta();

        this.modeloModelo = modelo;
        this.vistaLogin = login;
        this.vistaDash = dash;
        this.vistaMenuAdmin = menuAdmin;
        this.vistamydash = mydash;
        this.vistaAgregarProductos = agregarProductos;
        this.vistaVerProductos = verProductos;
        this.vistaModificarProducto = modificarProducto;
        this.vistaAgregarMarca = agregarMarca;
        this.vistaModificarMarca = modificarMarca;
        this.vistaVerMarcas = verMarcas;
        this.vistaAgregarUsuario = agregarUsuario;
        this.vistaVerUsuarios = verUsuarios;
        this.vistaModificarUsuario = modificarUsuario;
        this.vistaNuevaVenta = nuevaVenta;

        if (!cargaContent) {
            this.vistaLogin.btnAcceptPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    if (vistaLogin.carga == false) {
                        vistaLogin.carga = true;
                        cargaContent = true;
                        vistaLogin.btnAcceptPanel.setBackground(new Color(204, 204, 204));
                        vistaLogin.btnAcceptPanel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                        vistaLogin.btnAcceptLabel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                        vistaLogin.txtUsuario.setEnabled(false);
                        vistaLogin.txtClave.setEnabled(false);
                        vistaLogin.panelLoader.setVisible(true);

                        timer.start();
                    }
                }
            });

            this.vistaDash.panelButton1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(1);
                }
            });

            this.vistaDash.labelButton1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(1);
                }
            });

            this.vistaDash.panelButton3.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(2);
                }
            });

            this.vistaDash.labelButton3.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(2);
                }
            });

            this.vistaDash.btnVistaVentaLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(12);
                }
            });

            this.vistaDash.btnVistaVentaPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(12);
                }
            });

            this.vistaMenuAdmin.panelButtonProductoAgregar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(3);
                }
            });

            this.vistaMenuAdmin.labelButtonProductoAgregar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(3);
                }
            });

            this.vistaAgregarProductos.btnAcceptPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    vistaAgregarProductos.btnAcceptPanel.setBackground(new Color(153, 153, 153));
                    vistaAgregarProductos.carga = true;
                    if (vistaAgregarProductos.comboMarca.getSelectedIndex() == 0) {
                        vistaAgregarProductos.btnAcceptLabel.setText("OCURRIO UN ERROR. VERIFIQUE LA MARCA!");
                        resetMsg(1);
                    } else {
                        vistaAgregarProductos.btnAcceptLabel.setText("INSERTANDO...");
                        timerGuardarProducto.start();
                    }
                }
            });

            this.vistaAgregarProductos.btnAcceptLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    vistaAgregarProductos.btnAcceptPanel.setBackground(new Color(153, 153, 153));
                    vistaAgregarProductos.carga = true;
                    if (vistaAgregarProductos.comboMarca.getSelectedIndex() == 0) {
                        vistaAgregarProductos.btnAcceptLabel.setText("OCURRIO UN ERROR. VERIFIQUE LA MARCA!");
                        resetMsg(1);
                    } else {
                        vistaAgregarProductos.btnAcceptLabel.setText("INSERTANDO...");
                        timerGuardarProducto.start();
                    }
                }
            });

            this.vistaMenuAdmin.panelButtonProductoVer.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(4);
                }
            });

            this.vistaMenuAdmin.labelButtonProductoVer.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(4);
                }
            });

            this.vistaMenuAdmin.panelButtonProductomModificar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(5);
                }
            });

            this.vistaMenuAdmin.labelButtonProductoModificar.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(5);
                }
            });

            this.vistaMenuAdmin.btnAddMLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(6);
                }
            });

            this.vistaMenuAdmin.btnAddMPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(6);
                }
            });

            this.vistaMenuAdmin.btnModMLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(7);
                }
            });

            this.vistaMenuAdmin.btnModMPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(7);
                }
            });

            this.vistaMenuAdmin.btnVerMLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(8);
                }
            });

            this.vistaMenuAdmin.btnVerMPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(8);
                }
            });

            this.vistaMenuAdmin.btnAddUserLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(9);
                }
            });

            this.vistaMenuAdmin.btnAddUserPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(9);
                }
            });

            this.vistaMenuAdmin.btnVerUserLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(10);
                }
            });

            this.vistaMenuAdmin.btnVerUserPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(10);
                }
            });

            this.vistaMenuAdmin.btnModUserLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(11);
                }
            });

            this.vistaMenuAdmin.btnModUserPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    loadContent(11);
                }
            });

            this.vistaVerProductos.txtBuscarProducto.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    changed();
                }

                public void changed() {
                    Runnable doAssist = new Runnable() {
                        @Override
                        public void run() {
                            productosTabla(vistaVerProductos.txtBuscarProducto.getText());

                            vistaVerProductos.tabla.setModel(tdatos);
                        }
                    };
                    SwingUtilities.invokeLater(doAssist);
                }
            });

            this.vistaModificarProducto.txtBuscarProducto.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    changed();
                }

                public void changed() {
                    Runnable doAssist = new Runnable() {
                        @Override
                        public void run() {
                            tdatos = new DefaultTableModel();

                            productosTabla(vistaModificarProducto.txtBuscarProducto.getText());

                            vistaModificarProducto.tabla.setModel(tdatos);
                        }
                    };
                    SwingUtilities.invokeLater(doAssist);
                }
            });

            this.vistaModificarProducto.tabla.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    int rowIndex = vistaModificarProducto.tabla.getSelectedRow();

                    ResultSet response;
                    try {
                        response = modeloModelo.obtenerProducto(String.valueOf(vistaModificarProducto.tabla.getValueAt(rowIndex, 0)));
                        while (response.next()) {
                            vistaModificarProducto.txtIDProducto.setText(String.valueOf(response.getString("pr_id")));
                            vistaModificarProducto.txtCodigoProducto.setText(String.valueOf(response.getString("pr_codigoBarras")));
                            vistaModificarProducto.txtNombreProducto.setText(String.valueOf(response.getString("pr_nombre")));
                            vistaModificarProducto.txtPrecioProducto.setText(String.valueOf(response.getString("pr_precio")));
                            vistaModificarProducto.txtCostoProducto.setText(String.valueOf(response.getString("pr_costo")));
                            boolean a = true;
                            if (Integer.parseInt(response.getString("pr_visible")) == 0) {
                                a = false;
                            }
                            vistaModificarProducto.checkVisibleProducto.setSelected(a);
                            vistaModificarProducto.comboMarca.setSelectedItem(String.valueOf(response.getString("ma_id")) + ". " + String.valueOf(response.getString("ma_nombre")));
                        }
                    } catch (SQLException ex) {
                        //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            this.vistaModificarProducto.btnAcceptLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    vistaModificarProducto.btnAcceptPanel.setBackground(new Color(153, 153, 153));
                    vistaModificarProducto.carga = true;

                    vistaModificarProducto.btnAcceptLabel.setText("GUARDANDO...");
                    timerModificarProducto.start();

                }
            });

            this.vistaModificarProducto.btnAcceptPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    vistaModificarProducto.btnAcceptPanel.setBackground(new Color(153, 153, 153));
                    vistaModificarProducto.carga = true;

                    vistaModificarProducto.btnAcceptLabel.setText("GUARDANDO...");
                    timerModificarProducto.start();
                }
            });

            this.vistaAgregarMarca.btnAcceptLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    vistaAgregarMarca.btnAcceptPanel.setBackground(new Color(153, 153, 153));
                    vistaAgregarMarca.carga = true;

                    vistaAgregarMarca.btnAcceptLabel.setText("INSERTANDO...");
                    timerAgregarMarca.start();
                }
            });

            this.vistaAgregarMarca.btnAcceptPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    vistaAgregarMarca.btnAcceptPanel.setBackground(new Color(153, 153, 153));
                    vistaAgregarMarca.carga = true;

                    vistaAgregarMarca.btnAcceptLabel.setText("INSERTANDO...");
                    timerAgregarMarca.start();
                }
            });

            this.vistaModificarMarca.txtBuscarProducto.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    changed();
                }

                public void changed() {
                    Runnable doAssist = new Runnable() {
                        @Override
                        public void run() {
                            marcaTabla(vistaModificarMarca.txtBuscarProducto.getText());

                            vistaModificarMarca.tabla.setModel(tdatos);
                        }
                    };
                    SwingUtilities.invokeLater(doAssist);
                }
            });

            this.vistaModificarMarca.tabla.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    int rowIndex = vistaModificarMarca.tabla.getSelectedRow();
                    ResultSet response;
                    try {
                        response = modeloModelo.obtenerMarca(String.valueOf(vistaModificarMarca.tabla.getValueAt(rowIndex, 0)));
                        while (response.next()) {
                            vistaModificarMarca.txtIDMarca.setText(String.valueOf(response.getString("ma_id")));
                            vistaModificarMarca.txtNombreMarca.setText(String.valueOf(response.getString("ma_nombre")));
                        }
                    } catch (SQLException ex) {
                        //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            this.vistaModificarMarca.btnAcceptLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    vistaModificarMarca.btnAcceptPanel.setBackground(new Color(153, 153, 153));
                    vistaModificarMarca.carga = true;

                    vistaModificarMarca.btnAcceptLabel.setText("GUARDANDO...");
                    timerModificarMarca.start();
                }
            });

            this.vistaModificarMarca.btnAcceptPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    vistaModificarMarca.btnAcceptPanel.setBackground(new Color(153, 153, 153));
                    vistaModificarMarca.carga = true;

                    vistaModificarMarca.btnAcceptLabel.setText("GUARDANDO...");
                    timerModificarMarca.start();
                }
            });

            this.vistaVerMarcas.txtBuscarProducto.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    changed();
                }

                public void changed() {
                    Runnable doAssist = new Runnable() {
                        @Override
                        public void run() {
                            marcaTabla(vistaVerMarcas.txtBuscarProducto.getText());

                            vistaVerMarcas.tabla.setModel(tdatos);
                        }
                    };
                    SwingUtilities.invokeLater(doAssist);
                }
            });

            this.vistaAgregarUsuario.btnAcceptLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    vistaAgregarUsuario.btnAcceptPanel.setBackground(new Color(153, 153, 153));
                    vistaAgregarUsuario.carga = true;

                    vistaAgregarUsuario.btnAcceptLabel.setText("AGREGANDO...");
                    timerAgregarUsuario.start();

                }
            });

            this.vistaAgregarUsuario.btnAcceptPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    vistaModificarProducto.btnAcceptPanel.setBackground(new Color(153, 153, 153));
                    vistaModificarProducto.carga = true;

                    vistaModificarProducto.btnAcceptLabel.setText("AGREGANDO...");
                    timerAgregarUsuario.start();
                }
            });

            this.vistaVerUsuarios.txtBuscarProducto.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    changed();
                }

                public void changed() {
                    Runnable doAssist = new Runnable() {
                        @Override
                        public void run() {
                            tdatos = new DefaultTableModel();

                            usuariosTabla(vistaVerUsuarios.txtBuscarProducto.getText());

                            vistaVerUsuarios.tabla.setModel(tdatos);
                        }
                    };
                    SwingUtilities.invokeLater(doAssist);
                }
            });

            this.vistaModificarUsuario.txtBuscarProducto.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    changed();
                }

                public void changed() {
                    Runnable doAssist = new Runnable() {
                        @Override
                        public void run() {
                            tdatos = new DefaultTableModel();

                            usuariosTabla(vistaModificarUsuario.txtBuscarProducto.getText());

                            vistaModificarUsuario.tabla.setModel(tdatos);
                        }
                    };
                    SwingUtilities.invokeLater(doAssist);
                }
            });

            this.vistaModificarUsuario.tabla.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    int rowIndex = vistaModificarUsuario.tabla.getSelectedRow();
                    ResultSet response;
                    try {
                        response = modeloModelo.buscarUsuario(String.valueOf(vistaModificarUsuario.tabla.getValueAt(rowIndex, 0)));
                        while (response.next()) {
                            ResultSet Persona;
                            Persona = modeloModelo.obtenerPersona(response.getString("dp_id"));
                            while (Persona.next()) {
                                vistaModificarUsuario.txtIDPersona.setText(String.valueOf(response.getString("dp_id")));
                                vistaModificarUsuario.txtNombresPersona.setText(String.valueOf(Persona.getString("dp_nombres")));
                                vistaModificarUsuario.txtApp.setText(String.valueOf(Persona.getString("dp_apellidoPaterno")));
                                vistaModificarUsuario.txtApm.setText(String.valueOf(Persona.getString("dp_apellidoMaterno")));

                                String fnac = String.valueOf(Persona.getString("dp_fechaNacimiento"));
                                String[] fnacParts = fnac.split("-");

                                vistaModificarUsuario.calendarFechaPersona.getYearChooser().setYear(Integer.parseInt(fnacParts[0]));
                                vistaModificarUsuario.calendarFechaPersona.getMonthChooser().setMonth(Integer.parseInt(fnacParts[1]) - 1);
                                vistaModificarUsuario.calendarFechaPersona.getDayChooser().setDay(Integer.parseInt(fnacParts[2]));
                            }

                            vistaModificarUsuario.txtIDUsuario.setText(String.valueOf(response.getString("us_id")));
                            vistaModificarUsuario.txtNombreUsuario.setText(String.valueOf(response.getString("us_usuario")));
                            vistaModificarUsuario.txtCorreo.setText(String.valueOf(response.getString("us_email")));
                            vistaModificarUsuario.txtClave.setText(String.valueOf(response.getString("us_clave")));
                            vistaModificarUsuario.txtTipoUsuario.setSelectedItem(String.valueOf(response.getString("tu_id")) + ". " + String.valueOf(response.getString("tu_tipo")));
                        }
                    } catch (SQLException ex) {
                        //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            this.vistaModificarUsuario.btnAcceptLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    vistaModificarUsuario.btnAcceptPanel.setBackground(new Color(153, 153, 153));
                    vistaModificarUsuario.carga = true;

                    vistaModificarUsuario.btnAcceptLabel.setText("GUARDANDO...");
                    timerModificarUsuario.start();
                }
            });

            this.vistaModificarUsuario.btnAcceptPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    vistaModificarUsuario.btnAcceptPanel.setBackground(new Color(153, 153, 153));
                    vistaModificarUsuario.carga = true;

                    vistaModificarUsuario.btnAcceptLabel.setText("GUARDANDO...");
                    timerModificarUsuario.start();
                }
            });

            this.vistaNuevaVenta.txtBuscarProducto.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    changed();
                }

                public void changed() {
                    Runnable doAssist = new Runnable() {
                        @Override
                        public void run() {
                            productosTablaCarrito(vistaNuevaVenta.txtBuscarProducto.getText());

                            vistaNuevaVenta.tablaProductos.setModel(tdatos);
                        }
                    };
                    SwingUtilities.invokeLater(doAssist);
                }
            });

            this.vistaNuevaVenta.tablaProductos.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    int rowIndex = vistaNuevaVenta.tablaProductos.getSelectedRow();
                    ResultSet response;
                    try {
                        response = modeloModelo.obtenerMarca(String.valueOf(vistaModificarMarca.tabla.getValueAt(rowIndex, 0)));
                        while (response.next()) {
                            vistaModificarMarca.txtIDMarca.setText(String.valueOf(response.getString("ma_id")));
                            vistaModificarMarca.txtNombreMarca.setText(String.valueOf(response.getString("ma_nombre")));
                        }
                    } catch (SQLException ex) {
                        //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            this.vistaNuevaVenta.tablaCarrito.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    int rowIndex = vistaNuevaVenta.tablaCarrito.getSelectedRow();
                    ResultSet response;
                    try {
                        response = modeloModelo.obtenerProdCarrito(String.valueOf(vistaNuevaVenta.tablaCarrito.getValueAt(rowIndex, 0)));
                        while (response.next()) {
                            vistaNuevaVenta.txtIDCarrito.setText(String.valueOf(response.getString("cr_id")));
                            vistaNuevaVenta.txtCodigoProducto.setText(String.valueOf(response.getString("pr_codigoBarras")));
                            vistaNuevaVenta.txtNombreProducto.setText(String.valueOf(response.getString("pr_nombre")));
                            vistaNuevaVenta.txtPrecioProducto.setText(String.valueOf(response.getString("cr_precio")));
                            vistaNuevaVenta.txtCantidad.setText(String.valueOf(response.getString("cr_cantidad")));
                        }
                    } catch (SQLException ex) {
                        //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            this.vistaNuevaVenta.txtCantidad.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    changed();
                }

                public void changed() {
                    Runnable doAssist = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (modelo.actualizarCantidad(vistaNuevaVenta.txtIDCarrito.getText(), vistaNuevaVenta.txtCantidad.getText())) {
                                    carritoTabla();
                                    vistaNuevaVenta.tablaCarrito.setModel(cdatos);
                                }
                            } catch (SQLException ex) {
                                ////Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    };
                    SwingUtilities.invokeLater(doAssist);
                }
            });

            this.vistaNuevaVenta.btnEliminarPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    vistaNuevaVenta.panelEliminarArticulo.setVisible(true);
                }
            });

            this.vistaNuevaVenta.btnEliminarLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    vistaNuevaVenta.panelEliminarArticulo.setVisible(true);
                }
            });

            this.vistaNuevaVenta.btnDelPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    int rowIndex = vistaNuevaVenta.tablaCarrito.getSelectedRow();
                    ResultSet response;
                    try {
                        if (modelo.eliminarCarrito(vistaNuevaVenta.txtIDCarrito.getText())) {
                            vistaNuevaVenta.txtIDCarrito.setText("");
                            vistaNuevaVenta.txtCodigoProducto.setText("");
                            vistaNuevaVenta.txtNombreProducto.setText("");
                            vistaNuevaVenta.txtPrecioProducto.setText("");
                            vistaNuevaVenta.txtCantidad.setText("");
                            carritoTabla();
                            vistaNuevaVenta.tablaCarrito.setModel(cdatos);
                        }
                    } catch (SQLException ex) {
                        ////Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vistaNuevaVenta.panelEliminarArticulo.setVisible(false);
                }
            });

            this.vistaNuevaVenta.btnDelLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    int rowIndex = vistaNuevaVenta.tablaCarrito.getSelectedRow();
                    ResultSet response;
                    try {
                        if (modelo.eliminarCarrito(vistaNuevaVenta.txtIDCarrito.getText())) {
                            vistaNuevaVenta.txtIDCarrito.setText("");
                            vistaNuevaVenta.txtCodigoProducto.setText("");
                            vistaNuevaVenta.txtNombreProducto.setText("");
                            vistaNuevaVenta.txtPrecioProducto.setText("");
                            vistaNuevaVenta.txtCantidad.setText("");
                            carritoTabla();
                            vistaNuevaVenta.tablaCarrito.setModel(cdatos);
                        }
                    } catch (SQLException ex) {
                        ////Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vistaNuevaVenta.panelEliminarArticulo.setVisible(false);
                }
            });

            this.vistaNuevaVenta.btnNoDelLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    vistaNuevaVenta.panelEliminarArticulo.setVisible(false);
                }
            });

            this.vistaNuevaVenta.btnNoDelPananel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    vistaNuevaVenta.panelEliminarArticulo.setVisible(false);
                }
            });

            this.vistaNuevaVenta.tablaProductos.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    vistaNuevaVenta.panelAgregarArticulo.setVisible(true);
                }
            });

            this.vistaNuevaVenta.btnNoAddPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    vistaNuevaVenta.panelAgregarArticulo.setVisible(false);
                }
            });

            this.vistaNuevaVenta.btnNoAddLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    vistaNuevaVenta.panelAgregarArticulo.setVisible(false);
                }
            });

            this.vistaNuevaVenta.btnAddPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    int rowIndex = vistaNuevaVenta.tablaProductos.getSelectedRow();
                    try {
                        if (modelo.insertarCarrito(String.valueOf(vistaNuevaVenta.tablaProductos.getValueAt(rowIndex, 0)))) {
                            carritoTabla();
                            vistaNuevaVenta.tablaCarrito.setModel(cdatos);
                        }
                    } catch (SQLException ex) {
                        //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vistaNuevaVenta.panelAgregarArticulo.setVisible(false);
                }
            });

            this.vistaNuevaVenta.btnAddLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    int rowIndex = vistaNuevaVenta.tablaProductos.getSelectedRow();
                    try {
                        if (modelo.insertarCarrito(String.valueOf(vistaNuevaVenta.tablaProductos.getValueAt(rowIndex, 0)))) {
                            carritoTabla();
                            vistaNuevaVenta.tablaCarrito.setModel(cdatos);
                        }
                    } catch (SQLException ex) {
                        //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vistaNuevaVenta.panelAgregarArticulo.setVisible(false);
                }
            });

            this.vistaNuevaVenta.btnNoFinLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    vistaNuevaVenta.panelFinalizar.setVisible(false);
                }
            });

            this.vistaNuevaVenta.btnNoFinPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    vistaNuevaVenta.panelFinalizar.setVisible(false);
                }
            });

            this.vistaNuevaVenta.btnFinLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    vistaNuevaVenta.btnAcceptPanel.setBackground(new Color(153, 153, 153));
                    vistaNuevaVenta.carga = true;
                    try {
                        if (modeloModelo.obtenerCantidad() == null || modeloModelo.obtenerCantidad().equals("0")) {
                            cargaContent = false;
                            vistaNuevaVenta.btnAcceptLabel.setText("OCURRIO UN ERROR. VERIFIQUE EL CARRITO!");
                            resetMsg(7);
                        } else if (String.valueOf(vistaNuevaVenta.comboMetodos.getSelectedItem()).equals("Selecciona un metodo de pago")) {
                            cargaContent = false;
                            vistaNuevaVenta.btnAcceptLabel.setText("OCURRIO UN ERROR. VERIFIQUE EL METODO DE PAGO!");
                            resetMsg(7);
                        } else if (vistaNuevaVenta.txtPago.getText().equals("") || vistaNuevaVenta.txtPago.getText().isEmpty()) {
                            cargaContent = false;
                            vistaNuevaVenta.btnAcceptLabel.setText("OCURRIO UN ERROR. VERIFIQUE LA CANTIDAD DE PAGO!");
                            resetMsg(7);
                        } else {
                            vistaNuevaVenta.btnAcceptLabel.setText("FINALIZANDO...");
                            timerFinalizarVenta.start();
                        }
                    } catch (SQLException ex) {
                        ////Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vistaNuevaVenta.panelFinalizar.setVisible(false);
                }
            });

            this.vistaNuevaVenta.btnFinPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    cargaContent = true;
                    vistaNuevaVenta.btnAcceptPanel.setBackground(new Color(153, 153, 153));
                    vistaNuevaVenta.carga = true;
                    try {
                        if (modeloModelo.obtenerCantidad() == null || modeloModelo.obtenerCantidad().equals("0")) {
                            cargaContent = false;
                            vistaNuevaVenta.btnAcceptLabel.setText("OCURRIO UN ERROR. VERIFIQUE EL CARRITO!");
                            resetMsg(7);
                        } else if (String.valueOf(vistaNuevaVenta.comboMetodos.getSelectedItem()).equals("Selecciona un metodo de pago")) {
                            cargaContent = false;
                            vistaNuevaVenta.btnAcceptLabel.setText("OCURRIO UN ERROR. VERIFIQUE EL METODO DE PAGO!");
                            resetMsg(7);
                        } else if (vistaNuevaVenta.txtPago.getText().equals("") || vistaNuevaVenta.txtPago.getText().isEmpty()) {
                            cargaContent = false;
                            vistaNuevaVenta.btnAcceptLabel.setText("OCURRIO UN ERROR. VERIFIQUE LA CANTIDAD DE PAGO!");
                            resetMsg(7);
                        } else {
                            vistaNuevaVenta.btnAcceptLabel.setText("FINALIZANDO...");
                            timerFinalizarVenta.start();
                        }
                    } catch (SQLException ex) {
                        ////Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vistaNuevaVenta.panelFinalizar.setVisible(false);
                }
            });

            this.vistaNuevaVenta.btnAcceptLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    vistaNuevaVenta.panelFinalizar.setVisible(true);
                }
            });

            this.vistaNuevaVenta.btnAcceptPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    vistaNuevaVenta.panelFinalizar.setVisible(true);
                }
            });

            this.vistaNuevaVenta.txtPago.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    changed();
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    changed();
                }

                public void changed() {
                    Runnable doAssist = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (vistaNuevaVenta.txtPago.getText().isEmpty() || vistaNuevaVenta.txtPago.getText().equals("")) {
                                    vistaNuevaVenta.txtPago.setText("");
                                } else {
                                    vistaNuevaVenta.txtCambio.setText(String.valueOf(Float.parseFloat(vistaNuevaVenta.txtPago.getText()) - Float.parseFloat(vistaNuevaVenta.txtTotal.getText())));

                                }
                            } catch (Exception e) {
                            }
                        }
                    };
                    SwingUtilities.invokeLater(doAssist);
                }
            });
        }
    }

    public void iniciar() {
        this.modeloModelo.conectar();
        this.vistaLogin.dispose();
        this.vistaLogin.setTitle("BusSoft");
        this.vistaLogin.setLocationRelativeTo(null);
        this.vistaLogin.bgLabel.setIcon(getScaledImage("/imagenes/bg.jpg", 360, 580));
        this.vistaLogin.setResizable(false);
//        this.vistaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Already there
//        this.vistaLogin.setUndecorated(true);   
        this.vistaLogin.setVisible(true);
    }

    public void loadContent(int option) {
        this.contentInt = option;
        this.vistaDash.jContent.removeAll();
        this.vistaDash.jContent.revalidate();
        this.vistaDash.jContent.repaint();
        this.vistaDash.panelLoader.setVisible(true);
        this.timerLoadContent.start();
    }

    private ImageIcon getScaledImage(String srcImg, int w, int h) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(srcImg)); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);
        return imageIcon;
    }

    private ImageIcon getImage(String srcImg, int w, int h) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(srcImg)); // load the image to a imageIcon         
        return imageIcon;
    }

    public void actionPerformed(ActionEvent e) {
    }

    int resetInt = 0;

    private void resetMsg(int opcion) {
        resetInt = opcion;
        msgLoad.start();
    }

    public void marcaTabla(String txtBuscar) {
        tdatos = new DefaultTableModel();
        tdatos.addColumn("ID");
        tdatos.addColumn("Nombre");

        ResultSet response;
        if (txtBuscar.length() != 0) {
            try {
                response = modeloModelo.buscarMarca(txtBuscar);
                while (response.next()) {
                    tdatos.addRow(new Object[]{String.valueOf(response.getString("ma_id")), String.valueOf(response.getString("ma_nombre"))});
                }
            } catch (SQLException ex) {
                //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                response = modeloModelo.obtenerMarcas();
                while (response.next()) {
                    tdatos.addRow(new Object[]{String.valueOf(response.getString("ma_id")), String.valueOf(response.getString("ma_nombre"))});
                }
            } catch (SQLException ex) {
                //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void productosTabla(String txtBuscar) {
        tdatos = new DefaultTableModel();

        tdatos.addColumn("Codigo");
        tdatos.addColumn("Nombre");
        tdatos.addColumn("Marca");
        tdatos.addColumn("Precio");
        tdatos.addColumn("Costo");
        tdatos.addColumn("Visible");

        ResultSet response;
        if (txtBuscar.length() != 0) {
            try {
                response = modeloModelo.buscarProductos(txtBuscar);
                while (response.next()) {
                    String a = "Si";
                    if (Integer.parseInt(response.getString("pr_visible")) == 0) {
                        a = "No";
                    }

                    tdatos.addRow(new Object[]{String.valueOf(response.getString("pr_codigoBarras")), String.valueOf(response.getString("pr_nombre")), String.valueOf(response.getString("ma_nombre")), String.valueOf(response.getString("pr_precio")), String.valueOf(response.getString("pr_costo")), a});
                }
            } catch (SQLException ex) {
                //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                response = modeloModelo.obtenerProductos();
                while (response.next()) {
                    String a = "Si";
                    if (Integer.parseInt(response.getString("pr_visible")) == 0) {
                        a = "No";
                    }
                    tdatos.addRow(new Object[]{String.valueOf(response.getString("pr_codigoBarras")), String.valueOf(response.getString("pr_nombre")), String.valueOf(response.getString("ma_nombre")), String.valueOf(response.getString("pr_precio")), String.valueOf(response.getString("pr_costo")), a});
                }
            } catch (SQLException ex) {
                //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void productosTablaCarrito(String txtBuscar) {
        tdatos = new DefaultTableModel();

        tdatos.addColumn("Codigo");
        tdatos.addColumn("Nombre");
        tdatos.addColumn("Marca");
        tdatos.addColumn("Precio");
        tdatos.addColumn("Costo");

        ResultSet response;
        if (txtBuscar.length() != 0) {
            try {
                response = modeloModelo.buscarProductosCarrito(txtBuscar);
                while (response.next()) {
                    tdatos.addRow(new Object[]{String.valueOf(response.getString("pr_codigoBarras")), String.valueOf(response.getString("pr_nombre")), String.valueOf(response.getString("ma_nombre")), String.valueOf(response.getString("pr_precio")), String.valueOf(response.getString("pr_costo"))});
                }
            } catch (SQLException ex) {
                //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                response = modeloModelo.obtenerProductosCarrito();
                while (response.next()) {
                    tdatos.addRow(new Object[]{String.valueOf(response.getString("pr_codigoBarras")), String.valueOf(response.getString("pr_nombre")), String.valueOf(response.getString("ma_nombre")), String.valueOf(response.getString("pr_precio")), String.valueOf(response.getString("pr_costo"))});
                }
            } catch (SQLException ex) {
                //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void carritoTabla() {
        cdatos = new DefaultTableModel();

        cdatos.addColumn("ID Carrito");
        cdatos.addColumn("Codigo Articulo");
        cdatos.addColumn("Articulo");
        cdatos.addColumn("Marca");
        cdatos.addColumn("Cantidad");
        cdatos.addColumn("Precio");
        cdatos.addColumn("Subtotal");

        ResultSet response;
        try {
            response = modeloModelo.obtenerCarrito();
            while (response.next()) {
                cdatos.addRow(new Object[]{
                    String.valueOf(response.getString("cr_id")),
                    String.valueOf(response.getString("pr_codigoBarras")),
                    String.valueOf(response.getString("pr_nombre")),
                    String.valueOf(response.getString("ma_nombre")),
                    String.valueOf(response.getString("cr_cantidad")),
                    String.valueOf(response.getString("cr_precio")),
                    String.valueOf(Float.parseFloat(response.getString("cr_precio")) * Float.parseFloat(response.getString("cr_cantidad")))
                });
            }

            vistaNuevaVenta.txtTotalPro.setText(modeloModelo.obtenerCantidad());
            vistaNuevaVenta.txtTotal.setText(modeloModelo.obtenerTotal());
        } catch (SQLException ex) {
            //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void usuariosTabla(String txtBuscar) {
        tdatos = new DefaultTableModel();
        tdatos.addColumn("ID");
        tdatos.addColumn("Tipo");
        tdatos.addColumn("Nombre de usuario");
        tdatos.addColumn("Correo");
        tdatos.addColumn("Clave");

        tdatos.addColumn("Nombres");
        tdatos.addColumn("Apellido Paterno");
        tdatos.addColumn("Apellido Materno");
        tdatos.addColumn("Fecha Nacimiento");

        ResultSet response;
        if (txtBuscar.length() != 0) {
            try {
                response = modeloModelo.buscarUsuario(txtBuscar);
                while (response.next()) {
                    ResultSet Persona;
                    String Nombres = null;
                    String App = null;
                    String Apm = null;
                    String fnac = null;

                    Persona = modeloModelo.obtenerPersona(response.getString("dp_id"));
                    while (Persona.next()) {
                        Nombres = String.valueOf(Persona.getString("dp_nombres"));
                        App = String.valueOf(Persona.getString("dp_apellidoPaterno"));
                        Apm = String.valueOf(Persona.getString("dp_apellidoMaterno"));
                        fnac = String.valueOf(Persona.getString("dp_fechaNacimiento"));
                    }

                    tdatos.addRow(new Object[]{
                        String.valueOf(response.getString("us_id")),
                        String.valueOf(response.getString("tu_tipo")),
                        String.valueOf(response.getString("us_usuario")),
                        String.valueOf(response.getString("us_email")),
                        String.valueOf(response.getString("us_clave")),
                        Nombres,
                        App,
                        Apm,
                        fnac
                    });
                }
            } catch (SQLException ex) {
                //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                response = modeloModelo.obtenerUsuarios();
                while (response.next()) {
                    ResultSet Persona;
                    String Nombres = null;
                    String App = null;
                    String Apm = null;
                    String fnac = null;

                    Persona = modeloModelo.obtenerPersona(response.getString("dp_id"));
                    while (Persona.next()) {
                        Nombres = String.valueOf(Persona.getString("dp_nombres"));
                        App = String.valueOf(Persona.getString("dp_apellidoPaterno"));
                        Apm = String.valueOf(Persona.getString("dp_apellidoMaterno"));
                        fnac = String.valueOf(Persona.getString("dp_fechaNacimiento"));
                    }

                    tdatos.addRow(new Object[]{
                        String.valueOf(response.getString("us_id")),
                        String.valueOf(response.getString("tu_tipo")),
                        String.valueOf(response.getString("us_usuario")),
                        String.valueOf(response.getString("us_email")),
                        String.valueOf(response.getString("us_clave")),
                        Nombres,
                        App,
                        Apm,
                        fnac
                    });
                }
            } catch (SQLException ex) {
                //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    Timer timer = new Timer(2000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            try {
                cargaContent = true;
                boolean response;
                response = modeloModelo.consultarUsuario(vistaLogin.txtUsuario.getText(), new String(vistaLogin.txtClave.getPassword()));
                if (!response) {
                    vistaLogin.panelLoader.setVisible(false);
                    vistaLogin.carga = false;
                    JOptionPane.showMessageDialog(null, "Usuario o clave incorrectos", "BusSoft - Alerta", JOptionPane.WARNING_MESSAGE);
                    vistaLogin.btnAcceptPanel.setBackground(new Color(18, 5, 58));
                    vistaLogin.btnAcceptPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    vistaLogin.btnAcceptLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    vistaLogin.txtUsuario.setEnabled(true);
                    vistaLogin.txtClave.setEnabled(true);
                } else {
                    vistaLogin.setVisible(false);
                    vistaDash.setLocationRelativeTo(null);
                    vistaDash.setResizable(false);
                    vistaDash.setExtendedState(vistaDash.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                    vistaDash.labelNombre.setText(modeloModelo.obtenerDatosUsuario("us_usuario"));
                    vistaDash.labelTipoUsuario.setText(modeloModelo.obtenerDatosUsuario("tu_tipo"));
                    
                    if(modeloModelo.obtenerDatosUsuario("tu_id").equals("1011")){
                        vistaDash.panelButton3.setVisible(false);
                    }
                    
                    timerHora.start();
                    
                    vistaDash.setVisible(true);
                    GraphicsDevice grafica = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                    grafica.setFullScreenWindow(vistaDash);
                    loadContent(1);
                }
            } catch (SQLException ex) {
                //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            timer.stop();
        }
    });

    Timer timerHora = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm");
            vistaDash.fechaActual.setText(dtf.format(LocalDateTime.now()));
            vistaDash.horaActual.setText(hora.format(LocalDateTime.now()));
        }
    });

    Timer timerLoadContent = new Timer(2000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            vistaDash.jContent.removeAll();
            switch (contentInt) {
                case 1 -> {
                    vistamydash.setSize(1588, 1000);
                    vistamydash.setLocation(0, 0);
                    try {
                        vistamydash.cUsuarios.setText(modeloModelo.obtenerCantidadUsuarios());
                        vistamydash.cVentas.setText(modeloModelo.obtenerCantidadVentas());
                        vistamydash.cGanancias.setText(String.valueOf(modeloModelo.obtenerGanancias()));
                    } catch (SQLException ex) {
                        //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vistaDash.jContent.add(vistamydash, BorderLayout.CENTER);
                }
                case 2 -> {
                    vistaMenuAdmin.setSize(1588, 1000);
                    vistaMenuAdmin.setLocation(0, 0);
                    vistaDash.jContent.add(vistaMenuAdmin, BorderLayout.CENTER);
                }
                case 3 -> {
                    vistaAgregarProductos.setSize(1588, 1000);
                    vistaAgregarProductos.setLocation(0, 0);

                    ResultSet response;
                    try {
                        response = modeloModelo.obtenerMarcas();
                        vistaAgregarProductos.comboMarca.removeAllItems();
                        vistaAgregarProductos.comboMarca.addItem("Selecciona una marca");
                        while (response.next()) {
                            vistaAgregarProductos.comboMarca.addItem(String.valueOf(response.getString("ma_id")) + ". " + String.valueOf(response.getString("ma_nombre")));
                        }
                    } catch (SQLException ex) {
                        //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    vistaDash.jContent.add(vistaAgregarProductos, BorderLayout.CENTER);
                }
                case 4 -> {
                    vistaVerProductos.setSize(1588, 1000);
                    vistaVerProductos.setLocation(0, 0);

                    productosTabla(vistaVerProductos.txtBuscarProducto.getText());

                    vistaVerProductos.tabla.setModel(tdatos);
                    vistaDash.jContent.add(vistaVerProductos, BorderLayout.CENTER);
                }
                case 5 -> {
                    vistaModificarProducto.setSize(1588, 1000);
                    vistaModificarProducto.setLocation(0, 0);

                    ResultSet response;
                    try {
                        response = modeloModelo.obtenerMarcas();
                        vistaModificarProducto.comboMarca.removeAllItems();
                        vistaModificarProducto.comboMarca.addItem("Selecciona una marca");
                        while (response.next()) {
                            vistaModificarProducto.comboMarca.addItem(String.valueOf(response.getString("ma_id")) + ". " + String.valueOf(response.getString("ma_nombre")));
                        }
                    } catch (SQLException ex) {
                        //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    productosTabla(vistaModificarProducto.txtBuscarProducto.getText());

                    vistaModificarProducto.tabla.setModel(tdatos);
                    vistaDash.jContent.add(vistaModificarProducto, BorderLayout.CENTER);
                }
                case 6 -> {
                    vistaAgregarMarca.setSize(1588, 1000);
                    vistaAgregarMarca.setLocation(0, 0);

                    vistaDash.jContent.add(vistaAgregarMarca, BorderLayout.CENTER);
                }
                case 7 -> {
                    vistaModificarMarca.setSize(1588, 1000);
                    vistaModificarMarca.setLocation(0, 0);

                    marcaTabla(vistaModificarMarca.txtBuscarProducto.getText());

                    vistaModificarMarca.tabla.setModel(tdatos);
                    vistaDash.jContent.add(vistaModificarMarca, BorderLayout.CENTER);
                }
                case 8 -> {
                    vistaVerMarcas.setSize(1588, 1000);
                    vistaVerMarcas.setLocation(0, 0);

                    marcaTabla(vistaVerMarcas.txtBuscarProducto.getText());

                    vistaVerMarcas.tabla.setModel(tdatos);
                    vistaDash.jContent.add(vistaVerMarcas, BorderLayout.CENTER);
                }
                case 9 -> {
                    vistaAgregarUsuario.setSize(1588, 1000);
                    vistaAgregarUsuario.setLocation(0, 0);

                    ResultSet response;
                    try {
                        response = modeloModelo.obtenerTiposUsuario();
                        vistaAgregarUsuario.comboTipoUsuario.removeAllItems();
                        vistaAgregarUsuario.comboTipoUsuario.addItem("Selecciona un tipo de usuario");
                        while (response.next()) {
                            vistaAgregarUsuario.comboTipoUsuario.addItem(String.valueOf(response.getString("tu_id")) + ". " + String.valueOf(response.getString("tu_tipo")));
                        }
                    } catch (SQLException ex) {
                        //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    vistaDash.jContent.add(vistaAgregarUsuario, BorderLayout.CENTER);
                }
                case 10 -> {
                    vistaVerUsuarios.setSize(1588, 1000);
                    vistaVerUsuarios.setLocation(0, 0);

                    usuariosTabla(vistaVerUsuarios.txtBuscarProducto.getText());

                    vistaVerUsuarios.tabla.setModel(tdatos);
                    vistaDash.jContent.add(vistaVerUsuarios, BorderLayout.CENTER);
                }
                case 11 -> {
                    vistaModificarUsuario.setSize(1588, 1000);
                    vistaModificarUsuario.setLocation(0, 0);

                    ResultSet response;
                    try {
                        response = modeloModelo.obtenerTiposUsuario();
                        vistaModificarUsuario.txtTipoUsuario.removeAllItems();
                        while (response.next()) {
                            vistaModificarUsuario.txtTipoUsuario.addItem(String.valueOf(response.getString("tu_id")) + ". " + String.valueOf(response.getString("tu_tipo")));
                        }
                    } catch (SQLException ex) {
                        //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    usuariosTabla(vistaModificarUsuario.txtBuscarProducto.getText());

                    vistaModificarUsuario.tabla.setModel(tdatos);
                    vistaDash.jContent.add(vistaModificarUsuario, BorderLayout.CENTER);
                }
                case 12 -> {
                    vistaNuevaVenta.setSize(1588, 1000);
                    vistaNuevaVenta.setLocation(0, 0);

                    //vistaNuevaVenta.txtCantidadCarrito.setDocument(new SoloNumeros());
                    vistaNuevaVenta.panelAgregarArticulo.setVisible(false);
                    vistaNuevaVenta.panelEliminarArticulo.setVisible(false);
                    vistaNuevaVenta.panelFinalizar.setVisible(false);

                    ResultSet response;
                    try {
                        response = modeloModelo.obtenerMetodos();
                        vistaNuevaVenta.comboMetodos.removeAllItems();
                        vistaNuevaVenta.comboMetodos.addItem("Selecciona un metodo de pago");
                        while (response.next()) {
                            vistaNuevaVenta.comboMetodos.addItem(String.valueOf(response.getString("mp_id")) + ". " + String.valueOf(response.getString("mp_name")));
                        }
                    } catch (SQLException ex) {
                        //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    productosTablaCarrito(vistaNuevaVenta.txtBuscarProducto.getText());
                    carritoTabla();

                    vistaNuevaVenta.tablaProductos.setModel(tdatos);
                    vistaNuevaVenta.tablaCarrito.setModel(cdatos);
                    vistaDash.jContent.add(vistaNuevaVenta, BorderLayout.CENTER);
                }

                default -> {
                    vistamydash.setSize(1588, 1000);
                    vistamydash.setLocation(0, 0);
                    vistaDash.jContent.add(vistamydash, BorderLayout.CENTER);
                }
            }
            cargaContent = true;
            vistaDash.jContent.revalidate();
            vistaDash.jContent.repaint();
            vistaDash.panelLoader.setVisible(false);
            timerLoadContent.stop();
        }
    });

    Timer msgLoad = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (resetInt == 1) {
                vistaAgregarProductos.carga = false;
                vistaAgregarProductos.btnAcceptPanel.setBackground(new Color(249, 204, 15));
                vistaAgregarProductos.btnAcceptLabel.setForeground(new Color(0, 0, 0));
                vistaAgregarProductos.btnAcceptLabel.setText("AGREGAR");
            } else if (resetInt == 2) {
                vistaModificarProducto.carga = false;
                vistaModificarProducto.btnAcceptPanel.setBackground(new Color(249, 204, 15));
                vistaModificarProducto.btnAcceptLabel.setForeground(new Color(0, 0, 0));
                vistaModificarProducto.btnAcceptLabel.setText("GUARDAR");
            } else if (resetInt == 3) {
                vistaAgregarMarca.carga = false;
                vistaAgregarMarca.btnAcceptPanel.setBackground(new Color(249, 204, 15));
                vistaAgregarMarca.btnAcceptLabel.setForeground(new Color(0, 0, 0));
                vistaAgregarMarca.btnAcceptLabel.setText("AGREGAR");
            } else if (resetInt == 4) {
                vistaAgregarUsuario.carga = false;
                vistaAgregarUsuario.btnAcceptPanel.setBackground(new Color(249, 204, 15));
                vistaAgregarUsuario.btnAcceptLabel.setForeground(new Color(0, 0, 0));
                vistaAgregarUsuario.btnAcceptLabel.setText("AGREGAR");
            } else if (resetInt == 5) {
                vistaModificarMarca.carga = false;
                vistaModificarMarca.btnAcceptPanel.setBackground(new Color(249, 204, 15));
                vistaModificarMarca.btnAcceptLabel.setForeground(new Color(0, 0, 0));
                vistaModificarMarca.btnAcceptLabel.setText("GUARDAR");
            } else if (resetInt == 6) {
                vistaModificarUsuario.carga = false;
                vistaModificarUsuario.btnAcceptPanel.setBackground(new Color(249, 204, 15));
                vistaModificarUsuario.btnAcceptLabel.setForeground(new Color(0, 0, 0));
                vistaModificarUsuario.btnAcceptLabel.setText("GUARDAR");
            } else if (resetInt == 7) {
                vistaNuevaVenta.carga = false;
                vistaNuevaVenta.btnAcceptPanel.setBackground(new Color(249, 204, 15));
                vistaNuevaVenta.btnAcceptLabel.setForeground(new Color(0, 0, 0));
                vistaNuevaVenta.btnAcceptLabel.setText("FINALIZAR VENTA");
            }
            cargaContent = true;
            msgLoad.stop();
        }
    });

    Timer timerGuardarProducto = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (modeloModelo.insertarProducto(vistaAgregarProductos.txtCodigoProducto.getText(), vistaAgregarProductos.txtNombreProducto.getText(), vistaAgregarProductos.txtPrecioProducto.getText(), vistaAgregarProductos.txtCostoProducto.getText(), vistaAgregarProductos.comboMarca.getSelectedIndex())) {
                    vistaAgregarProductos.btnAcceptLabel.setText("SE AGREGO CORRECTAMENTE!");
                } else {
                    vistaAgregarProductos.btnAcceptLabel.setText("OCURRIO UN ERROR. VERIFIQUE LOS DATOS!");
                }
                resetMsg(1);
                cargaContent = true;
            } catch (SQLException ex) {
                vistaAgregarProductos.btnAcceptLabel.setText("OCURRIO UN ERROR, POSIBLE CODIGO DE BARRAS YA DADO DE ALTA. VERFIQUE LOS DATOS!");
                resetMsg(1);
                cargaContent = true;
            }
            timerGuardarProducto.stop();
        }
    });

    Timer timerModificarProducto = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (modeloModelo.modificarProducto(vistaModificarProducto.txtIDProducto.getText(), vistaModificarProducto.txtCodigoProducto.getText(), vistaModificarProducto.txtNombreProducto.getText(), vistaModificarProducto.txtPrecioProducto.getText(), vistaModificarProducto.txtCostoProducto.getText(), (String) vistaModificarProducto.comboMarca.getSelectedItem(), vistaModificarProducto.checkVisibleProducto.isSelected())) {
                    vistaModificarProducto.btnAcceptLabel.setText("SE GUARDO CORRECTAMENTE!");

                    productosTabla(vistaModificarProducto.txtBuscarProducto.getText());

                    vistaModificarProducto.tabla.setModel(tdatos);
                } else {
                    vistaModificarProducto.btnAcceptLabel.setText("OCURRIO UN ERROR. VERIFIQUE LOS DATOS!");
                }
                resetMsg(2);
                cargaContent = true;
            } catch (SQLException ex) {
                vistaModificarProducto.btnAcceptLabel.setText("OCURRIO UN ERROR, POSIBLE CODIGO DE BARRAS YA DADO DE ALTA. VERFIQUE LOS DATOS!");
                resetMsg(2);
                cargaContent = true;
            }
            timerModificarProducto.stop();
        }
    });

    Timer timerAgregarMarca = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (modeloModelo.insertarMarca(vistaAgregarMarca.txtNombreMarca.getText())) {
                    vistaAgregarMarca.btnAcceptLabel.setText("SE AGREGO CORRECTAMENTE!");
                }
                resetMsg(3);
                cargaContent = true;
            } catch (SQLException ex) {
                vistaAgregarMarca.btnAcceptLabel.setText("OCURRIO UN ERROR, POSIBLE NOMBRE DE MARCA YA DADO DE ALTA. VERFIQUE LOS DATOS!");
                resetMsg(3);
                cargaContent = true;
            }
            timerAgregarMarca.stop();
        }
    });

    Timer timerModificarMarca = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (modeloModelo.modificarMarca(vistaModificarMarca.txtIDMarca.getText(), vistaModificarMarca.txtNombreMarca.getText())) {
                    vistaModificarMarca.btnAcceptLabel.setText("SE GUARDO CORRECTAMENTE!");

                    marcaTabla(vistaModificarMarca.txtBuscarProducto.getText());

                    vistaModificarMarca.tabla.setModel(tdatos);
                }
            } catch (SQLException ex) {
                vistaModificarMarca.btnAcceptLabel.setText("OCURRIO UN ERROR, POSIBLE NOMBRE DE MARCA YA DADO DE ALTA. VERFIQUE LOS DATOS!");
            }
            resetMsg(5);
            cargaContent = true;

            timerModificarMarca.stop();
        }
    });

    Timer timerAgregarUsuario = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (modeloModelo.insertarUsuario(vistaAgregarUsuario.txtNombrePersona.getText(), vistaAgregarUsuario.txtAppPersona.getText(), vistaAgregarUsuario.txtApmPersona.getText(), String.valueOf(vistaAgregarUsuario.calendarFechaPersona.getYearChooser().getYear()) + "-" + String.valueOf(vistaAgregarUsuario.calendarFechaPersona.getMonthChooser().getMonth() + 1) + "-" + String.valueOf(vistaAgregarUsuario.calendarFechaPersona.getDayChooser().getDay()), vistaAgregarUsuario.txtNombreUsuario.getText(), vistaAgregarUsuario.txtCorreoUsuario.getText(), new String(vistaAgregarUsuario.txtClaveUsuario.getPassword()), (String) vistaAgregarUsuario.comboTipoUsuario.getSelectedItem())) {
                    vistaAgregarUsuario.btnAcceptLabel.setText("SE AGREGO CORRECTAMENTE!");
                }
            } catch (SQLException ex) {
                //Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                vistaAgregarUsuario.btnAcceptLabel.setText("OCURRIO UN ERROR. VERIFIQUE LOS DATOS!");
            }
            resetMsg(4);
            cargaContent = true;
            timerAgregarUsuario.stop();
        }
    });

    Timer timerModificarUsuario = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (modeloModelo.modificarUsuario(vistaModificarUsuario.txtNombresPersona.getText(), vistaModificarUsuario.txtApp.getText(), vistaModificarUsuario.txtApm.getText(), String.valueOf(vistaModificarUsuario.calendarFechaPersona.getYearChooser().getYear()) + "-" + String.valueOf(vistaModificarUsuario.calendarFechaPersona.getMonthChooser().getMonth() + 1) + "-" + String.valueOf(vistaModificarUsuario.calendarFechaPersona.getDayChooser().getDay()), vistaModificarUsuario.txtNombreUsuario.getText(), vistaModificarUsuario.txtCorreo.getText(), new String(vistaModificarUsuario.txtClave.getPassword()), (String) vistaModificarUsuario.txtTipoUsuario.getSelectedItem(), vistaModificarUsuario.txtIDUsuario.getText(), vistaModificarUsuario.txtIDPersona.getText())) {
                    vistaModificarUsuario.btnAcceptLabel.setText("SE GUARDO CORRECTAMENTE!");

                    usuariosTabla(vistaModificarUsuario.txtBuscarProducto.getText());

                    vistaModificarUsuario.tabla.setModel(tdatos);
                }
            } catch (SQLException ex) {
                vistaModificarUsuario.btnAcceptLabel.setText("OCURRIO UN ERROR. VERFIQUE LOS DATOS!");
            }
            resetMsg(6);
            cargaContent = true;

            timerModificarUsuario.stop();
        }
    });

    Timer timerFinalizarVenta = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (modeloModelo.actualizarVenta(modeloModelo.obtenerTotal(), (String) vistaNuevaVenta.comboMetodos.getSelectedItem())) {
                    vistaNuevaVenta.btnAcceptLabel.setText("SE FINALIZO CORRECTAMENTE!");
                    productosTablaCarrito(vistaNuevaVenta.txtBuscarProducto.getText());
                    carritoTabla();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    
                    float total = 0;
                    String data = "";
                    ResultSet response;
                    ResultSet response2;
                    try {
                        response = modeloModelo.obtenerUnCarrito(String.valueOf(Integer.parseInt(modeloModelo.obtenerDatosUsuario("vt_id")) - 1));
                       
                        while (response.next()) {                            
                            total = total + Float.parseFloat(response.getString("pr_precio")) * Float.parseFloat(response.getString("cr_cantidad"));
                            data = data
                            + String.valueOf(response.getString("cr_cantidad")) + "    "
                            + String.valueOf(response.getString("pr_nombre")) + "               "
                            + String.valueOf(response.getString("pr_precio")) + "     "
                            + String.valueOf(Float.parseFloat(response.getString("pr_precio")) * Float.parseFloat(response.getString("cr_cantidad"))) + "\n";
                        }
                    } catch (SQLException ex) {
                        ////Logger.getLogger(controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    String ruta = "venta-" + String.valueOf(Integer.parseInt(modeloModelo.obtenerDatosUsuario("vt_id")) - 1) + ".txt";
                    String contenido = "Punto de venta\n"
                    + "BUSSOFT\n"
                    + "JESUS NAVARRO SALCIDO\n"
                    + "Calle Santa Maria Del Valle, 2\n"
                    + "47184 - Arandas Jalisco\n"
                    + "Telefono: 3481468309\n"
                    + "\n"
                    + (String) dtf.format(LocalDateTime.now()) + "\n"
                    + "--------------------------------------------------\n"
                    + "C.     ARTICULO                    PRECIO     IMPORTE\n"
                    + "--------------------------------------------------\n"        
                    + data        
                    + "--------------------------------------------------\n"
                    + "                                   TOTAL: $"+total+"\n"
                    + "--------------------------------------------------\n"
                    + "                                PAGO CON: " + vistaNuevaVenta.txtPago.getText() +"\n"
                    + "                                  CAMBIO: " + vistaNuevaVenta.txtCambio.getText() + "    \n"
                    + "--------------------------------------------------\n"
                    + "Le atendio: "+modeloModelo.obtenerDatosUsuario("us_usuario")+"\n"
                    + "--------------------------------------------------\n";
                    
                    File file = new File(ruta);
                    // Si el archivo no existe es creado
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(contenido);
                    bw.close();
                    
                    vistaNuevaVenta.tablaProductos.setModel(tdatos);
                    vistaNuevaVenta.tablaCarrito.setModel(cdatos);

                    vistaNuevaVenta.txtIDCarrito.setText("");
                    vistaNuevaVenta.txtCodigoProducto.setText("");
                    vistaNuevaVenta.txtNombreProducto.setText("");
                    vistaNuevaVenta.txtPrecioProducto.setText("");
                    vistaNuevaVenta.comboMetodos.setSelectedItem("Selecciona un metodo de pago");
                    vistaNuevaVenta.txtPago.setText("");
                    vistaNuevaVenta.txtCambio.setText("$0");
                    vistaNuevaVenta.txtCantidad.setText("");
                    vistaNuevaVenta.txtTotalPro.setText("0");
                    vistaNuevaVenta.txtTotal.setText("$0");
                    
                }
            } catch (SQLException ex) {
                vistaNuevaVenta.btnAcceptLabel.setText("OCURRIO UN ERROR. VERFIQUE LOS DATOS!");
            } catch (IOException ex) {
                //vistaNuevaVenta.btnAcceptLabel.setText("OCURRIO UN ERROR. VERFIQUE LOS DATOS!");
            }
            resetMsg(7);
            cargaContent = true;

            timerFinalizarVenta.stop();
        }
    });

    public class SoloNumeros extends PlainDocument {

        @Override
        public void insertString(int arg0, String arg1, AttributeSet arg2) throws BadLocationException {
            for (int i = 0; i < arg1.length(); i++) {
                if (!Character.isDigit(arg1.charAt(i))) {
                    return;
                }
            }
            super.insertString(arg0, arg1, arg2);
        }

    }
}
