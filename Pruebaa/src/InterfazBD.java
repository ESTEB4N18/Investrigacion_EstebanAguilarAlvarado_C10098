import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class InterfazBD extends JFrame {

    private static final String URL = "jdbc:mysql://localhost:3306/proyectofinalbd?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "2003";
    
    private Connection connection;

    // Componentes para el login
    private JTextField txtUsuario, txtPassword;

    // Componentes para las acciones
    private JTextField txtCedula, txtNombre1, txtNombre2, txtApellido1, txtApellido2, txtCorreo;
    private JTextArea textAreaResultados;

    public InterfazBD() {
        setTitle("Sistema de Gestión - Base de Datos");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 240, 240));
        mostrarLogin();
    }

    // Conexión a la base de datos
    private void conectarBaseDeDatos() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    // Mostrar la interfaz de login
    private void mostrarLogin() {
        JPanel panelLogin = new JPanel();
        panelLogin.setLayout(new GridLayout(3, 2));
        panelLogin.setBackground(new Color(102, 205, 170));
        panelLogin.setBorder(BorderFactory.createTitledBorder("Login"));

        panelLogin.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panelLogin.add(txtUsuario);

        panelLogin.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelLogin.add(txtPassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(60, 179, 113));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.addActionListener(e -> verificarLogin());
        panelLogin.add(btnLogin);

        add(panelLogin, BorderLayout.CENTER);
    }

    // Verificar las credenciales del login
    private void verificarLogin() {
        String usuario = txtUsuario.getText();
        String contraseña = new String(((JPasswordField) txtPassword).getPassword());

        if ("admin".equals(usuario) && "admin".equals(contraseña)) {
            conectarBaseDeDatos();
            mostrarMenu();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.");
        }
    }

    // Mostrar el menú principal
    private void mostrarMenu() {
        getContentPane().removeAll();
        
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(5, 1));
        menuPanel.setBackground(new Color(70, 130, 180));

        JButton btnInsertar = new JButton("Insertar Usuario");
        btnInsertar.setBackground(new Color(30, 144, 255));
        btnInsertar.setForeground(Color.WHITE);
        btnInsertar.addActionListener(e -> mostrarPanelInsertar());
        menuPanel.add(btnInsertar);

        JButton btnConsultar = new JButton("Consultar Usuario");
        btnConsultar.setBackground(new Color(30, 144, 255));
        btnConsultar.setForeground(Color.WHITE);
        btnConsultar.addActionListener(e -> mostrarPanelConsultar());
        menuPanel.add(btnConsultar);

        JButton btnActualizar = new JButton("Actualizar Usuario");
        btnActualizar.setBackground(new Color(30, 144, 255));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.addActionListener(e -> mostrarPanelActualizar());
        menuPanel.add(btnActualizar);

        JButton btnEliminar = new JButton("Eliminar Usuario");
        btnEliminar.setBackground(new Color(30, 144, 255));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(e -> mostrarPanelEliminar());
        menuPanel.add(btnEliminar);
        
        JButton btnSalir = new JButton("Salir");
        btnSalir.setBackground(new Color(255, 99, 71));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.addActionListener(e -> salirAplicacion());
        menuPanel.add(btnSalir);

        add(menuPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // Mostrar el panel de inserción
    private void mostrarPanelInsertar() {
        JPanel panelInsertar = new JPanel();
        panelInsertar.setLayout(new GridLayout(7, 2));
        panelInsertar.setBackground(new Color(255, 228, 196));
        panelInsertar.setBorder(BorderFactory.createTitledBorder("Insertar Usuario"));

        panelInsertar.add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        panelInsertar.add(txtCedula);

        panelInsertar.add(new JLabel("Nombre1:"));
        txtNombre1 = new JTextField();
        panelInsertar.add(txtNombre1);

        panelInsertar.add(new JLabel("Nombre2:"));
        txtNombre2 = new JTextField();
        panelInsertar.add(txtNombre2);

        panelInsertar.add(new JLabel("Apellido1:"));
        txtApellido1 = new JTextField();
        panelInsertar.add(txtApellido1);

        panelInsertar.add(new JLabel("Apellido2:"));
        txtApellido2 = new JTextField();
        panelInsertar.add(txtApellido2);

        panelInsertar.add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        panelInsertar.add(txtCorreo);

        JButton btnInsertar = new JButton("Insertar");
        btnInsertar.setBackground(new Color(34, 139, 34));
        btnInsertar.setForeground(Color.WHITE);
        btnInsertar.addActionListener(e -> insertarUsuario());
        panelInsertar.add(btnInsertar);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 99, 71));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(e -> mostrarMenu());
        panelInsertar.add(btnVolver);

        add(panelInsertar, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // Método para insertar usuario
    private void insertarUsuario() {
        String cedula = txtCedula.getText();
        String nombre1 = txtNombre1.getText();
        String nombre2 = txtNombre2.getText();
        String apellido1 = txtApellido1.getText();
        String apellido2 = txtApellido2.getText();
        String correo = txtCorreo.getText();

        // Llamamos al procedimiento almacenado
        String query = "CALL InsertarUsuario(?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cedula);
            stmt.setString(2, nombre1);
            stmt.setString(3, nombre2);
            stmt.setString(4, apellido1);
            stmt.setString(5, apellido2);
            stmt.setString(6, correo);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Usuario insertado correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al insertar: " + e.getMessage());
        }
    }

    // Mostrar el panel de consulta
    private void mostrarPanelConsultar() {
        JPanel panelConsulta = new JPanel();
        panelConsulta.setLayout(new GridLayout(2, 2));
        panelConsulta.setBackground(new Color(255, 228, 196));
        panelConsulta.setBorder(BorderFactory.createTitledBorder("Consultar Usuario"));

        panelConsulta.add(new JLabel("Cédula:"));
        JTextField txtConsultaCedula = new JTextField();
        panelConsulta.add(txtConsultaCedula);

        JButton btnConsultar = new JButton("Consultar");
        btnConsultar.setBackground(new Color(34, 139, 34));
        btnConsultar.setForeground(Color.WHITE);
        btnConsultar.addActionListener(e -> consultarUsuario(txtConsultaCedula.getText()));
        panelConsulta.add(btnConsultar);

        // Panel de resultados
        textAreaResultados = new JTextArea(5, 40);
        textAreaResultados.setEditable(false);
        JScrollPane scrollResultados = new JScrollPane(textAreaResultados);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(255, 99, 71));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(e -> mostrarMenu());
        panelConsulta.add(btnVolver);

        add(panelConsulta, BorderLayout.CENTER);
        add(scrollResultados, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    // Método para consultar usuario
    private void consultarUsuario(String cedula) {
        String query = "CALL ConsultarUsuario(?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cedula);
            ResultSet rs = stmt.executeQuery();

            textAreaResultados.setText(""); // Limpiar resultados anteriores

            if (rs.next()) {
                textAreaResultados.append("Cédula: " + rs.getString("Cedula") + "\n");
                textAreaResultados.append("Nombre1: " + rs.getString("Nombre1") + "\n");
                textAreaResultados.append("Nombre2: " + rs.getString("Nombre2") + "\n");
                textAreaResultados.append("Apellido1: " + rs.getString("Apellido1") + "\n");
                textAreaResultados.append("Apellido2: " + rs.getString("Apellido2") + "\n");
                textAreaResultados.append("Correo: " + rs.getString("Correo") + "\n");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el usuario.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al consultar: " + e.getMessage());
        }
    }

    // Salir de la aplicación
    private void salirAplicacion() {
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazBD().setVisible(true));
    }

    private void mostrarPanelActualizar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void mostrarPanelEliminar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
