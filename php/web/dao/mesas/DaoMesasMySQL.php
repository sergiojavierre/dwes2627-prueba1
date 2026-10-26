<?

include_once __DIR__ . '/DaoMesas.php';
include_once __DIR__ . '../../../entities/Mesa.php';

class DaoMesasMySQL implements DaoMesas
{
    private $conn;

    public function __construct($conn)
    {
        $this->conn = $conn;
    }

    function get()
    {
        $query = 'SELECT * from mesas';
        $stmt = $this->conn->prepare($query);
        $stmt->execute();

        $mesas = [];
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $mesa = new Mesa($row['id']);
            $mesas[] = $mesa;
        }
        return $mesas;
    }

    function getPedidos($id)
    {
        $query = '
        select p.*, pl.precio from 
        pedidos p
        join platos pl on p.plato = pl.nombre
        WHERE mesa = :id';
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':id', $id);
        $stmt->execute();

        $pedidos = [];
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $pedido = new Pedido(
                new Mesa($row['mesa']),
                new Plato($row['plato'], $row['precio']),
                $row['cantidad']
            );
            $pedidos[] = $pedido;
        }
        return $pedidos;
    }

    function addPedido($mesa, $plato)
    {
        //busco si existe el pedido
        $query = 'SELECT * from pedidos WHERE mesa = :mesa AND plato = :plato';
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':mesa', $mesa);
        $stmt->bindParam(':plato', $plato);
        $stmt->execute();

        if ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            //si existe, actualizo la cantidad
            $query = 'UPDATE pedidos SET cantidad = cantidad + 1 WHERE mesa = :mesa AND plato = :plato';
            $stmt = $this->conn->prepare($query);
            $stmt->bindParam(':mesa', $mesa);
            $stmt->bindParam(':plato', $plato);
            $stmt->execute();
        } else {
            //si no existe, lo creo
            $query = 'INSERT INTO pedidos (mesa, plato, cantidad) VALUES (:mesa, :plato, 1)';
            $stmt = $this->conn->prepare($query);
            $stmt->bindParam(':mesa', $mesa);
            $stmt->bindParam(':plato', $plato);
            $stmt->execute();
        }
    }

    function limpiarMesa($id)
    {
        $query = 'DELETE FROM pedidos WHERE mesa = :id';
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':id', $id);
        $stmt->execute();
    }
}
