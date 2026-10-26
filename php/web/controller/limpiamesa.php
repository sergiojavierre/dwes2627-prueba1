<?

include_once __DIR__ . '/../dao/DaoFactory.php';

$mesa = $_POST['mesa'];

$daoFactory = DaoFactory::getDao();
$daoMesas = $daoFactory->getDaoMesas();

$daoMesas->limpiarMesa($mesa);

header('Location: ../mesa.php?id=' . $mesa);
exit;
