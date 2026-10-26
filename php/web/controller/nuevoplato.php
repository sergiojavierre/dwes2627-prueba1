<?

include_once __DIR__ . '/../dao/DaoFactory.php';

$nombre = $_POST['plato'];
$mesa = $_POST['mesa'];

$daoFactory = DaoFactory::getDao();
$daoMesas = $daoFactory->getDaoMesas();

$daoMesas->addPedido($mesa, $nombre);

header('Location: ../mesa.php?id=' . $mesa);
exit;
