
import java.util.ArrayList;
import java.util.List;

public class Lista {

    private ArrayList<Paqueteria> serviEntrega;

    public Lista() {
        serviEntrega = new ArrayList<>();
    }

    public ArrayList<Paqueteria> getServiEntrega() {
        return serviEntrega;
    }

    // 1. REGISTRAR PAQUETES
    public void adicionarElementos(Paqueteria paquete) throws Exception {
        if (buscarPaquete(paquete.getTracking()) != null) {
            throw new Exception("El tracking ya existe");
        }
        serviEntrega.add(paquete);
    }

    // 2. LISTAR PAQUETES
    public String listarPaquetes() {
        String resultado = "";

        for (Paqueteria p : serviEntrega) {
            resultado += p.toString() + "\n";
        }

        return resultado;
    }

    // 3. BUSCAR POR TRACKING
    public Paqueteria buscarPaquete(int tracking) {
        for (Paqueteria p : serviEntrega) {
            if (p.getTracking() == tracking) {
                return p;
            }
        }
        return null;
    }
    //Modificar Info
    public void editar(int tracking, double peso,
                       String ciudadRecepcion,
                       String ciudadEntrega,
                       String cedulaReceptor) throws Exception {

        Paqueteria p = buscarPaquete(tracking);

        if (p == null) {
            throw new Exception("Paquete no encontrado");
        }
        p.setPeso(peso);
        p.setCiudadRecepcion(ciudadRecepcion);
        p.setCiudadEntrega(ciudadEntrega);
        p.setCedulaReceptor(cedulaReceptor);

}
    // 5. CAMBIAR ESTADO
    public String estadoPaquete(int tracking) {
        Paqueteria p = buscarPaquete(tracking);

        if (p == null) {
            throw new IllegalArgumentException("Paquete no encontrado");
        }

        if (p.getEstado().equals("Receptado")) {
            p.setEstado("Enviado");
            return "Enviado";
        }

        if (p.getEstado().equals("Enviado")) {
            p.setEstado("Recibido");
            return "Recibido";
        }

        return "Finalizado";
    }
    // 6. RECURSIVIDAD: TOTAL DE PAQUETES
    public int sumarTotalPaquetes() {
        return contarPaquetes(0);
    }

    private int contarPaquetes(int indice) {
        if (indice == serviEntrega.size()) {
            return 0;
        }

        return 1 + contarPaquetes(indice + 1);
    }
    // 7. RECURSIVIDAD: PESO TOTAL
    public double sumarTotalPeso() {
        return calcularPeso(0);
    }

    private double calcularPeso(int indice) {
        if (indice == serviEntrega.size()) {
            return 0;
        }

        return serviEntrega.get(indice).getPeso()
                + calcularPeso(indice + 1);
    }



    // RECURSIVIDAD: PESO DE PAQUETES RECIBIDOS POR CIUDAD
    public double sumarTotalPesoCiudad(String ciudad) {
        return pesoCiudad(ciudad, 0);
    }

    private double pesoCiudad(String ciudad, int indice) {
        if (indice == serviEntrega.size()) {
            return 0;
        }
        Paqueteria p = serviEntrega.get(indice);
        double peso = 0;
        if (p.getCiudadEntrega().equalsIgnoreCase(ciudad)
                && p.getEstado().equals("Recibido")) {
            peso = p.getPeso();
        }
        return peso + pesoCiudad(ciudad, indice + 1);
    }

    // RECURSIVIDAD: TOTAL DE PAQUETES EN UN ESTADO
    public int sumarTotalPaquetesEstado(String estado) {
        return contarEstado(estado, 0);
    }

    private int contarEstado(String estado, int indice) {
        if (indice == serviEntrega.size()) {
            return 0;
        }
        int cantidad = 0;
        if (serviEntrega.get(indice).getEstado().equalsIgnoreCase(estado)) {
            cantidad = 1;
        }
        return cantidad + contarEstado(estado, indice + 1);
    }

    // BURBUJA: TRACKING ASCENDENTE (SIN MODIFICAR LA LISTA ORIGINAL)
    public List<Paqueteria> ordenarPaquetesPorTracking() {
        ArrayList<Paqueteria> ordenada = new ArrayList<>(serviEntrega);
        for (int i = 0; i < ordenada.size() - 1; i++) {
            for (int j = 0; j < ordenada.size() - 1 - i; j++) {
                if (ordenada.get(j).getTracking() > ordenada.get(j + 1).getTracking()) {
                    Paqueteria auxiliar = ordenada.get(j);
                    ordenada.set(j, ordenada.get(j + 1));
                    ordenada.set(j + 1, auxiliar);
                }
            }
        }
        return ordenada;
    }

    // INSERCION: PESO ASCENDENTE (SIN MODIFICAR LA LISTA ORIGINAL)
    public List<Paqueteria> ordenarPaquetesPorPeso() {
        ArrayList<Paqueteria> ordenada = new ArrayList<>(serviEntrega);
        for (int i = 1; i < ordenada.size(); i++) {
            Paqueteria actual = ordenada.get(i);
            int j = i - 1;
            while (j >= 0 && ordenada.get(j).getPeso() > actual.getPeso()) {
                ordenada.set(j + 1, ordenada.get(j));
                j--;
            }
            ordenada.set(j + 1, actual);
        }
        return ordenada;
    }

    // BUSQUEDA BINARIA EN UNA LISTA PREVIAMENTE ORDENADA POR TRACKING
    public int busquedaBinaria(List<Paqueteria> ordenada, int tracking) {
        int inicio = 0;
        int fin = ordenada.size() - 1;
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            if (ordenada.get(medio).getTracking() == tracking) {
                return medio;
            }
            if (ordenada.get(medio).getTracking() < tracking) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return -1;
    }

    // BUSQUEDA POR CEDULA Y ESTADO AL MISMO TIEMPO
    public List<Paqueteria> buscarPaquetesPorCedulaYEstado(String cedula, String estado) {
        ArrayList<Paqueteria> resultado = new ArrayList<>();
        for (Paqueteria p : serviEntrega) {
            if (p.getCedulaReceptor().equals(cedula)
                    && p.getEstado().equalsIgnoreCase(estado)) {
                resultado.add(p);
            }
        }
        return resultado;
    }
}
