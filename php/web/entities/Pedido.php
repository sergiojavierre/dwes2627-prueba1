<?

class Pedido
{
    private $mesa;
    private $plato;
    private $cantidad;

    public function __construct($mesa, $plato, $cantidad)
    {
        $this->mesa = $mesa;
        $this->plato = $plato;
        $this->cantidad = $cantidad;
    }

    public function getMesa()
    {
        return $this->mesa;
    }

    public function getPlato()
    {
        return $this->plato;
    }

    public function getCantidad()
    {
        return $this->cantidad;
    }
}
