<?

interface DaoMesas
{
    function get();
    function getPedidos($id);
    function addPedido($mesa, $plato);
    function limpiarMesa($id);
}
