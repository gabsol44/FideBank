
package fidebank;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    SistemaBanco banco = new SistemaBanco();
    JPanel panel;

    JTextField txtIdentificacion;
    JPasswordField txtPin;
    Cliente clienteActual;

    public VentanaPrincipal() {

        setTitle("FideBank - Cajero Automático");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        mostrarInicio();
    }

    // Crear el panel de cada pantalla
    public void crearPanel() {

        if (panel != null) {
            remove(panel);
        }

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(108, 175, 250));

        add(panel);
    }

    // Titulo del banco
    public void agregarTitulo() {

        JLabel titulo = new JLabel("FideBank", SwingConstants.CENTER);
        titulo.setBounds(100, 25, 280, 40);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo);

        JLabel subtitulo = new JLabel(
                "Sistema de Cajero Automático",
                SwingConstants.CENTER);
        subtitulo.setBounds(90, 70, 300, 30);
        subtitulo.setForeground(Color.WHITE);
        panel.add(subtitulo);
    }

    // Crear botones
    public JButton crearBoton(String texto, int x, int y, int ancho) {

        JButton boton = new JButton(texto);
        boton.setBounds(x, y, ancho, 40);
        boton.setBackground(new Color(210, 20, 65));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);

        panel.add(boton);
        return boton;
    }

    // Actualizar pantalla
    public void actualizarPantalla() {

        panel.revalidate();
        panel.repaint();
        revalidate();
        repaint();
    }

    // Inicio de sesion
    public void mostrarInicio() {

        clienteActual = null;
        crearPanel();
        agregarTitulo();

        JLabel titulo = new JLabel(
                "Inicio de sesión", SwingConstants.CENTER);
        titulo.setBounds(100, 130, 280, 30);
        titulo.setForeground(Color.WHITE);
        panel.add(titulo);

        JLabel lblId = new JLabel("Identificación del cliente:");
        lblId.setBounds(100, 175, 280, 25);
        lblId.setForeground(Color.WHITE);
        panel.add(lblId);

        txtIdentificacion = new JTextField();
        txtIdentificacion.setBounds(100, 205, 280, 30);
        panel.add(txtIdentificacion);

        JLabel lblPin = new JLabel("PIN de seguridad:");
        lblPin.setBounds(100, 255, 280, 25);
        lblPin.setForeground(Color.WHITE);
        panel.add(lblPin);

        txtPin = new JPasswordField();
        txtPin.setBounds(100, 285, 280, 30);
        panel.add(txtPin);

        JButton btnIngresar = crearBoton(
                "Ingresar", 150, 345, 180);
        btnIngresar.addActionListener(e -> iniciarSesion());

        JButton btnRegistro = crearBoton(
                "Registrar cliente", 150, 400, 180);
        btnRegistro.addActionListener(e -> mostrarRegistro());

        actualizarPantalla();
    }

    // Registro de clientes
    public void mostrarRegistro() {

        crearPanel();
        agregarTitulo();

        JLabel titulo = new JLabel(
                "Registro de clientes", SwingConstants.CENTER);
        titulo.setBounds(100, 115, 280, 30);
        titulo.setForeground(Color.WHITE);
        panel.add(titulo);

        JLabel lblId = new JLabel("Identificación del cliente:");
        lblId.setBounds(100, 155, 280, 25);
        lblId.setForeground(Color.WHITE);
        panel.add(lblId);

        JTextField campoId = new JTextField();
        campoId.setBounds(100, 180, 280, 30);
        panel.add(campoId);

        JLabel lblNombre = new JLabel("Nombre completo:");
        lblNombre.setBounds(100, 220, 280, 25);
        lblNombre.setForeground(Color.WHITE);
        panel.add(lblNombre);

        JTextField campoNombre = new JTextField();
        campoNombre.setBounds(100, 245, 280, 30);
        panel.add(campoNombre);

        JLabel lblPin = new JLabel("PIN de seguridad:");
        lblPin.setBounds(100, 285, 280, 25);
        lblPin.setForeground(Color.WHITE);
        panel.add(lblPin);

        JPasswordField campoPin = new JPasswordField();
        campoPin.setBounds(100, 310, 280, 30);
        panel.add(campoPin);

        JButton btnRegistrar = crearBoton(
                "Registrar cliente", 150, 365, 180);

        btnRegistrar.addActionListener(e -> {

            String id = campoId.getText().trim();
            String nombre = campoNombre.getText().trim();
            String pin = new String(campoPin.getPassword());

            if (banco.registrarCliente(id, nombre, pin)) {

                JOptionPane.showMessageDialog(this,
                        "Cliente registrado correctamente");
                mostrarInicio();

            } else {

                JOptionPane.showMessageDialog(this,
                        "Revise los datos o la identificación ya existe");
            }
        });

        JButton btnVolver = crearBoton(
                "Volver al inicio", 150, 420, 180);
        btnVolver.addActionListener(e -> mostrarInicio());

        actualizarPantalla();
    }

    // Validar inicio de sesion
    public void iniciarSesion() {

        String id = txtIdentificacion.getText().trim();
        String pin = new String(txtPin.getPassword());

        Cliente cliente = banco.validarAcceso(id, pin);

        if (cliente != null) {
            clienteActual = cliente;
            mostrarMenu();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Identificación o PIN incorrecto");
        }
    }

    // Menu principal
    public void mostrarMenu() {

        if (clienteActual == null) {
            mostrarInicio();
            return;
        }

        crearPanel();
        agregarTitulo();

        JLabel titulo = new JLabel(
                "Menú principal", SwingConstants.CENTER);
        titulo.setBounds(100, 105, 280, 30);
        titulo.setForeground(Color.WHITE);
        panel.add(titulo);

        JLabel bienvenida = new JLabel(
                "Bienvenido, " + clienteActual.nombre,
                SwingConstants.CENTER);
        bienvenida.setBounds(90, 140, 300, 25);
        bienvenida.setForeground(Color.WHITE);
        panel.add(bienvenida);

        JButton btnSaldo = crearBoton(
                "Consultar saldo", 55, 180, 175);
        btnSaldo.addActionListener(e -> mostrarConsultaSaldo());

        JButton btnRetiro = crearBoton(
                "Retirar dinero", 250, 180, 175);
        btnRetiro.addActionListener(e -> mostrarOperacion("Retiro"));

        JButton btnDeposito = crearBoton(
                "Depositar dinero", 55, 230, 175);
        btnDeposito.addActionListener(e -> mostrarOperacion("Deposito"));

        JButton btnTransferir = crearBoton(
                "Transferir fondos", 250, 230, 175);
        btnTransferir.addActionListener(e -> mostrarTransferencia());

        JButton btnMovimientos = crearBoton(
                "Consultar movimientos", 55, 280, 175);
        btnMovimientos.addActionListener(e -> mostrarMovimientos());

        JButton btnComprobante = crearBoton(
                "Ver comprobante", 250, 280, 175);
        btnComprobante.addActionListener(e -> mostrarComprobante());

        JButton btnCrear = crearBoton(
                "Crear cuenta", 120, 340, 240);
        btnCrear.addActionListener(e -> mostrarCrearCuenta());

        JButton btnSalir = crearBoton(
                "Cerrar sesión", 120, 395, 240);
        btnSalir.addActionListener(e -> mostrarInicio());

        actualizarPantalla();
    }

    // Apertura de cuentas
    public void mostrarCrearCuenta() {

        if (clienteActual == null) {
            mostrarInicio();
            return;
        }

        crearPanel();
        agregarTitulo();

        JLabel titulo = new JLabel(
                "Apertura de cuenta bancaria",
                SwingConstants.CENTER);
        titulo.setBounds(80, 105, 320, 30);
        titulo.setForeground(Color.WHITE);
        panel.add(titulo);

        JLabel lblId = new JLabel("Identificación del cliente:");
        lblId.setBounds(100, 140, 280, 25);
        lblId.setForeground(Color.WHITE);
        panel.add(lblId);

        JTextField campoId = new JTextField(
                clienteActual.identificacion);
        campoId.setBounds(100, 165, 280, 30);
        campoId.setEditable(false);
        panel.add(campoId);

        JLabel lblNumero = new JLabel("Número de cuenta:");
        lblNumero.setBounds(100, 205, 280, 25);
        lblNumero.setForeground(Color.WHITE);
        panel.add(lblNumero);

        JTextField campoNumero = new JTextField();
        campoNumero.setBounds(100, 230, 280, 30);
        panel.add(campoNumero);

        JLabel lblTipo = new JLabel("Tipo de cuenta:");
        lblTipo.setBounds(100, 270, 280, 25);
        lblTipo.setForeground(Color.WHITE);
        panel.add(lblTipo);

        JComboBox<String> comboTipo = new JComboBox<>(
                new String[]{"Ahorros", "Corriente"});
        comboTipo.setBounds(100, 295, 280, 30);
        panel.add(comboTipo);

        JLabel lblSaldo = new JLabel("Saldo inicial:");
        lblSaldo.setBounds(100, 335, 280, 25);
        lblSaldo.setForeground(Color.WHITE);
        panel.add(lblSaldo);

        JTextField campoSaldo = new JTextField();
        campoSaldo.setBounds(100, 360, 280, 30);
        panel.add(campoSaldo);

        JButton btnCrear = crearBoton(
                "Crear cuenta", 100, 420, 135);

        btnCrear.addActionListener(e -> {

            try {
                String numero = campoNumero.getText().trim();
                String tipo = comboTipo.getSelectedItem().toString();
                double saldo = Double.parseDouble(
                        campoSaldo.getText().trim());

                boolean creada = banco.crearCuenta(
                        clienteActual.identificacion,
                        numero, saldo, tipo);

                if (creada) {
                    JOptionPane.showMessageDialog(this,
                            "Cuenta creada correctamente");
                    mostrarMenu();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo crear la cuenta. Revise los datos.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese un saldo válido, por ejemplo: 50000");
            }
        });

        JButton btnVolver = crearBoton(
                "Volver al menú", 245, 420, 135);
        btnVolver.addActionListener(e -> mostrarMenu());

        actualizarPantalla();
    }

    // Obtener cuentas del cliente
    public JComboBox<String> obtenerCuentasCliente() {

        JComboBox<String> combo = new JComboBox<>();

        if (clienteActual != null) {

            for (Cuenta cuenta : banco.cuentas) {

                if (banco.verificarPropietario(
                        clienteActual.identificacion,
                        cuenta.numeroCuenta)) {

                    combo.addItem(cuenta.numeroCuenta);
                }
            }
        }

        return combo;
    }

    // Consulta de saldo
    public void mostrarConsultaSaldo() {

        if (clienteActual == null) {
            mostrarInicio();
            return;
        }

        crearPanel();
        agregarTitulo();

        JLabel titulo = new JLabel(
                "Consulta de saldo", SwingConstants.CENTER);
        titulo.setBounds(100, 125, 280, 30);
        titulo.setForeground(Color.WHITE);
        panel.add(titulo);

        JLabel lblCuenta = new JLabel(
                "Número de cuenta:", SwingConstants.CENTER);
        lblCuenta.setBounds(100, 175, 280, 25);
        lblCuenta.setForeground(Color.WHITE);
        panel.add(lblCuenta);

        JComboBox<String> comboCuentas = obtenerCuentasCliente();
        comboCuentas.setBounds(100, 205, 280, 30);
        panel.add(comboCuentas);

        JLabel lblSaldo = new JLabel(
                "Saldo disponible:", SwingConstants.CENTER);
        lblSaldo.setBounds(100, 260, 280, 25);
        lblSaldo.setForeground(Color.WHITE);
        panel.add(lblSaldo);

        JTextField campoSaldo = new JTextField();
        campoSaldo.setBounds(100, 290, 280, 30);
        campoSaldo.setEditable(false);
        campoSaldo.setHorizontalAlignment(JTextField.CENTER);
        panel.add(campoSaldo);

        Runnable actualizarSaldo = () -> {

            String numero = (String) comboCuentas.getSelectedItem();

            if (numero != null) {

                try {
                    double saldo = banco.consultarSaldo(
                            clienteActual.identificacion, numero);

                    campoSaldo.setText(
                            String.format("₡%,.2f", saldo));

                } catch (IllegalArgumentException ex) {
                    campoSaldo.setText("Cuenta no autorizada");
                }

            } else {
                campoSaldo.setText("No tiene cuentas registradas");
            }
        };

        comboCuentas.addActionListener(e -> actualizarSaldo.run());
        actualizarSaldo.run();

        JButton btnVolver = crearBoton(
                "Volver al menú", 70, 375, 160);
        btnVolver.addActionListener(e -> mostrarMenu());

        JButton btnSalir = crearBoton(
                "Cerrar sesión", 250, 375, 160);
        btnSalir.addActionListener(e -> mostrarInicio());

        actualizarPantalla();
    }

    // Depositos y retiros
    public void mostrarOperacion(String tipo) {

        if (clienteActual == null) {
            mostrarInicio();
            return;
        }

        crearPanel();
        agregarTitulo();

        String nombre = tipo.equals("Retiro")
                ? "Retiro de dinero" : "Depósito de dinero";

        JLabel titulo = new JLabel(
                nombre, SwingConstants.CENTER);
        titulo.setBounds(100, 125, 280, 30);
        titulo.setForeground(Color.WHITE);
        panel.add(titulo);

        JLabel lblCuenta = new JLabel(
                "Número de cuenta:", SwingConstants.CENTER);
        lblCuenta.setBounds(100, 175, 280, 25);
        lblCuenta.setForeground(Color.WHITE);
        panel.add(lblCuenta);

        JComboBox<String> comboCuentas = obtenerCuentasCliente();
        comboCuentas.setBounds(100, 205, 280, 30);
        panel.add(comboCuentas);

        JLabel lblMonto = new JLabel(
                tipo.equals("Retiro")
                        ? "Monto que desea retirar:"
                        : "Monto que desea depositar:",
                SwingConstants.CENTER);
        lblMonto.setBounds(100, 255, 280, 25);
        lblMonto.setForeground(Color.WHITE);
        panel.add(lblMonto);

        JTextField campoMonto = new JTextField();
        campoMonto.setBounds(100, 285, 280, 30);
        campoMonto.setHorizontalAlignment(JTextField.CENTER);
        panel.add(campoMonto);

        JLabel indicacion = new JLabel(
                "Ingrese un monto válido, por ejemplo: 10000",
                SwingConstants.CENTER);
        indicacion.setBounds(75, 320, 330, 25);
        indicacion.setForeground(Color.WHITE);
        panel.add(indicacion);

        JButton btnConfirmar = crearBoton(
                tipo.equals("Retiro")
                        ? "Confirmar retiro"
                        : "Confirmar depósito",
                140, 355, 200);

        btnConfirmar.addActionListener(e -> {

            String numero = (String) comboCuentas.getSelectedItem();

            if (numero == null) {
                JOptionPane.showMessageDialog(this,
                        "Primero debe crear una cuenta bancaria");
                return;
            }

            try {
                double monto = Double.parseDouble(
                        campoMonto.getText().trim());

                if (!Double.isFinite(monto) || monto <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "El monto debe ser mayor que cero");
                    return;
                }

                boolean realizada;

                if (tipo.equals("Retiro")) {
                    realizada = banco.retirar(
                            clienteActual.identificacion,
                            numero, monto);
                } else {
                    realizada = banco.depositar(
                            clienteActual.identificacion,
                            numero, monto);
                }

                if (realizada) {

                    double saldo = banco.consultarSaldo(
                            clienteActual.identificacion, numero);

                    JOptionPane.showMessageDialog(this,
                            "Operación realizada correctamente"
                            + "\nCuenta: " + numero
                            + "\nMonto: ₡" + monto
                            + "\nSaldo actual: ₡" + saldo);

                    mostrarMenu();

                } else {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo realizar la operación."
                            + "\nRevise el monto o el saldo disponible.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese un monto numérico válido");
            }
        });

        JButton btnVolver = crearBoton(
                "Volver al menú", 70, 420, 160);
        btnVolver.addActionListener(e -> mostrarMenu());

        JButton btnSalir = crearBoton(
                "Cerrar sesión", 250, 420, 160);
        btnSalir.addActionListener(e -> mostrarInicio());

        actualizarPantalla();
    }

    // Transferencias bancarias
    public void mostrarTransferencia() {

        if (clienteActual == null) {
            mostrarInicio();
            return;
        }

        crearPanel();
        agregarTitulo();

        JLabel titulo = new JLabel(
                "Transferencia bancaria", SwingConstants.CENTER);
        titulo.setBounds(100, 110, 280, 30);
        titulo.setForeground(Color.WHITE);
        panel.add(titulo);

        JLabel lblOrigen = new JLabel("Cuenta de origen:");
        lblOrigen.setBounds(100, 150, 280, 25);
        lblOrigen.setForeground(Color.WHITE);
        panel.add(lblOrigen);

        JComboBox<String> comboOrigen = obtenerCuentasCliente();
        comboOrigen.setBounds(100, 175, 280, 30);
        panel.add(comboOrigen);

        JLabel lblDestino = new JLabel("Cuenta de destino:");
        lblDestino.setBounds(100, 215, 280, 25);
        lblDestino.setForeground(Color.WHITE);
        panel.add(lblDestino);

        JTextField campoDestino = new JTextField();
        campoDestino.setBounds(100, 240, 280, 30);
        panel.add(campoDestino);

        JLabel lblMonto = new JLabel(
                "Monto que desea transferir:");
        lblMonto.setBounds(100, 280, 280, 25);
        lblMonto.setForeground(Color.WHITE);
        panel.add(lblMonto);

        JTextField campoMonto = new JTextField();
        campoMonto.setBounds(100, 305, 280, 30);
        panel.add(campoMonto);

        JButton btnTransferir = crearBoton(
                "Confirmar transferencia", 120, 355, 240);

        btnTransferir.addActionListener(e -> {

            String origen = (String) comboOrigen.getSelectedItem();
            String destino = campoDestino.getText().trim();

            if (origen == null) {
                JOptionPane.showMessageDialog(this,
                        "Primero debe crear una cuenta bancaria");
                return;
            }

            if (destino.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese la cuenta de destino");
                return;
            }

            try {
                double monto = Double.parseDouble(
                        campoMonto.getText().trim());

                if (!Double.isFinite(monto) || monto <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "El monto debe ser mayor que cero");
                    return;
                }

                if (origen.equals(destino)) {
                    JOptionPane.showMessageDialog(this,
                            "No puede transferir a la misma cuenta");
                    return;
                }

                int confirmacion = JOptionPane.showConfirmDialog(
                        this,
                        "¿Desea transferir ₡" + monto
                        + "\nDesde la cuenta: " + origen
                        + "\nHacia la cuenta: " + destino + "?",
                        "Confirmar transferencia",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion != JOptionPane.YES_OPTION) {
                    return;
                }

                boolean realizada = banco.transferir(
                        clienteActual.identificacion,
                        origen, destino, monto);

                if (realizada) {

                    JOptionPane.showMessageDialog(this,
                            "Transferencia realizada correctamente"
                            + "\nCuenta de origen: " + origen
                            + "\nCuenta de destino: " + destino
                            + "\nMonto transferido: ₡" + monto);

                    mostrarMenu();

                } else {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo realizar la transferencia."
                            + "\nVerifique las cuentas y el saldo disponible.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese un monto numérico válido");
            }
        });

        JButton btnVolver = crearBoton(
                "Volver al menú", 70, 420, 160);
        btnVolver.addActionListener(e -> mostrarMenu());

        JButton btnSalir = crearBoton(
                "Cerrar sesión", 250, 420, 160);
        btnSalir.addActionListener(e -> mostrarInicio());

        actualizarPantalla();
    }

    // Historial de movimientos
    public void mostrarMovimientos() {

        if (clienteActual == null) {
            mostrarInicio();
            return;
        }

        crearPanel();
        agregarTitulo();

        JLabel titulo = new JLabel(
                "Historial de movimientos",
                SwingConstants.CENTER);
        titulo.setBounds(100, 115, 280, 30);
        titulo.setForeground(Color.WHITE);
        panel.add(titulo);

        JLabel lblCuenta = new JLabel(
                "Seleccione la cuenta bancaria:",
                SwingConstants.CENTER);
        lblCuenta.setBounds(80, 155, 320, 25);
        lblCuenta.setForeground(Color.WHITE);
        panel.add(lblCuenta);

        JComboBox<String> comboCuentas = obtenerCuentasCliente();
        comboCuentas.setBounds(100, 185, 280, 30);
        panel.add(comboCuentas);

        JTextArea areaMovimientos = new JTextArea();
        areaMovimientos.setEditable(false);
        areaMovimientos.setLineWrap(true);
        areaMovimientos.setWrapStyleWord(true);
        areaMovimientos.setFont(
                new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(areaMovimientos);
        scroll.setBounds(45, 235, 390, 165);
        panel.add(scroll);

        Runnable actualizarMovimientos = () -> {

            String numero = (String) comboCuentas.getSelectedItem();

            if (numero == null) {
                areaMovimientos.setText(
                        "No tiene cuentas bancarias registradas.");
                return;
            }

            String movimientos = banco.consultarMovimientos(
                    clienteActual.identificacion, numero);

            boolean tieneMovimientos = false;

            for (Transaccion transaccion : banco.transacciones) {

                if (transaccion.numeroCuenta.equals(numero)) {
                    tieneMovimientos = true;
                    break;
                }
            }

            if (tieneMovimientos) {
                areaMovimientos.setText(movimientos);
            } else {
                areaMovimientos.setText(
                        "Cuenta: " + numero
                        + "\n\nNo existen movimientos registrados.");
            }

            areaMovimientos.setCaretPosition(0);
        };

        comboCuentas.addActionListener(
                e -> actualizarMovimientos.run());
        actualizarMovimientos.run();

        JButton btnVolver = crearBoton(
                "Volver al menú", 70, 425, 160);
        btnVolver.addActionListener(e -> mostrarMenu());

        JButton btnSalir = crearBoton(
                "Cerrar sesión", 250, 425, 160);
        btnSalir.addActionListener(e -> mostrarInicio());

        actualizarPantalla();
    }

    // Nueva pantalla de comprobantes bancarios
    public void mostrarComprobante() {

        if (clienteActual == null) {
            mostrarInicio();
            return;
        }

        crearPanel();
        agregarTitulo();

        JLabel titulo = new JLabel(
                "Comprobante bancario",
                SwingConstants.CENTER);
        titulo.setBounds(100, 115, 280, 30);
        titulo.setForeground(Color.WHITE);
        panel.add(titulo);

        JLabel lblCuenta = new JLabel(
                "Seleccione la cuenta bancaria:",
                SwingConstants.CENTER);
        lblCuenta.setBounds(80, 155, 320, 25);
        lblCuenta.setForeground(Color.WHITE);
        panel.add(lblCuenta);

        JComboBox<String> comboCuentas = obtenerCuentasCliente();
        comboCuentas.setBounds(100, 185, 280, 30);
        panel.add(comboCuentas);

        JTextArea areaComprobante = new JTextArea();
        areaComprobante.setEditable(false);
        areaComprobante.setFont(
                new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(areaComprobante);
        scroll.setBounds(65, 235, 350, 165);
        panel.add(scroll);

        // Mostrar el ultimo comprobante de la cuenta
        Runnable actualizarComprobante = () -> {

            String numero = (String) comboCuentas.getSelectedItem();

            if (numero == null) {

                areaComprobante.setText(
                        "No tiene cuentas bancarias registradas.");
                return;
            }

            String comprobante = banco.obtenerComprobante(
                    clienteActual.identificacion, numero);

            areaComprobante.setText(comprobante);
            areaComprobante.setCaretPosition(0);
        };

        comboCuentas.addActionListener(
                e -> actualizarComprobante.run());

        actualizarComprobante.run();

        JButton btnVolver = crearBoton(
                "Volver al menú", 70, 425, 160);
        btnVolver.addActionListener(e -> mostrarMenu());

        JButton btnSalir = crearBoton(
                "Cerrar sesión", 250, 425, 160);
        btnSalir.addActionListener(e -> mostrarInicio());

        actualizarPantalla();
    }
}
