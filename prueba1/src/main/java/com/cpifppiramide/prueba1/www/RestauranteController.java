package com.cpifppiramide.prueba1.www;

import com.cpifppiramide.prueba1.dao.DaoFactory;
import com.cpifppiramide.prueba1.entities.Mesa;
import com.cpifppiramide.prueba1.entities.Pedido;
import com.cpifppiramide.prueba1.entities.Plato;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class RestauranteController {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("mesas", daoFactory.getMesasDao().getAll());
        model.addAttribute("platos", daoFactory.getPlatosDao().getAll());
        return "index";
    }

    @GetMapping("/mesas/{id}")
    public String mesa(@PathVariable int id, Model model) {
        Mesa mesa = daoFactory.getMesasDao().getById(id);
        if (mesa == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mesa no encontrada");
        }
        List<Pedido> pedidos = daoFactory.getMesasDao().getPedidos(id);
        BigDecimal total = pedidos.stream()
                .map(pedido -> pedido.getPlato().getPrecio().multiply(BigDecimal.valueOf(pedido.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("mesa", mesa);
        model.addAttribute("platos", daoFactory.getPlatosDao().getAll());
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("total", total);
        return "mesa";
    }

    @PostMapping("/mesas/{id}/pedidos")
    public String addPedido(@PathVariable int id, @RequestParam String plato) {
        requireMesa(id);
        daoFactory.getMesasDao().addPedido(id, plato);
        return "redirect:/mesas/" + id;
    }

    @PostMapping("/mesas/{id}/limpiar")
    public String limpiarMesa(@PathVariable int id) {
        requireMesa(id);
        daoFactory.getMesasDao().limpiarMesa(id);
        return "redirect:/mesas/" + id;
    }

    @GetMapping("/platos/{nombre}/editar")
    public String editarPlato(@PathVariable String nombre, Model model) {
        Plato plato = daoFactory.getPlatosDao().getByNombre(nombre);
        if (plato == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato no encontrado");
        }
        model.addAttribute("plato", plato);
        return "editar";
    }

    @PostMapping("/platos/actualizar")
    public String actualizarPlato(@RequestParam String nombre, @RequestParam BigDecimal precio) {
        daoFactory.getPlatosDao().update(new Plato(nombre, precio));
        return "redirect:/";
    }

    private void requireMesa(int id) {
        if (daoFactory.getMesasDao().getById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mesa no encontrada");
        }
    }
}
