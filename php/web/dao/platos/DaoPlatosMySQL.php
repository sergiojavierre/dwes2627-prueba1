<?

include_once __DIR__ . '/DaoPlatos.php';
include_once __DIR__ . '../../../entities/Plato.php';

class DaoPlatosMySQL implements DaoPlatos
{
    private $conn;

    public function __construct($conn)
    {
        $this->conn = $conn;
    }

    function get()
    {
        $query = 'SELECT * from platos';
        $stmt = $this->conn->prepare($query);
        $stmt->execute();

        $platos = [];
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            $plato = new Plato($row['nombre'], $row['precio']);
            $platos[] = $plato;
        }
        return $platos;
    }

    function getByNombre($nombre)
    {
        $query = 'SELECT * from platos WHERE nombre = :nombre';
        $stmt = $this->conn->prepare($query);
        $stmt->bindParam(':nombre', $nombre);
        $stmt->execute();

        if ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            return new Plato($row['nombre'], $row['precio']);
        } else {
            return null;
        }
    }

    function update($plato)
    {
        $query = 'UPDATE platos SET precio = :precio WHERE nombre = :nombre';
        $stmt = $this->conn->prepare($query);
        $stmt->bindValue(':nombre', $plato->getNombre());
        $stmt->bindValue(':precio', $plato->getPrecio());
        $stmt->execute();
    }
}
