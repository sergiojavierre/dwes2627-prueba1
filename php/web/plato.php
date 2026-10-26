<?php

require_once __DIR__ . '/dao/DaoFactory.php';

$nombre = $_GET['nombre'];

$dao = DaoFactory::getDao();
$plato = $dao->getDaoPlatos()->getByNombre($nombre);

include_once __DIR__ . '/components/nav.php';

?>

<h1>Actualizar plato</h1>
<form action="/controller/actualizaplato.php" method="POST">
    <label>Nombre:</label>
    <input name="nombre" value="<? echo $plato->getNombre() ?>" readonly>
    <label>Precio (€):</label>
    <input name="precio" type="number" step="0.01" value="<? echo $plato->getPrecio() ?>">
    <button>Actualizar plato</button>
</form>