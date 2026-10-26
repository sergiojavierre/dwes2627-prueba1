<?php
require_once __DIR__ . '/../dao/DaoFactory.php';


$nombre = $_POST['nombre'];
$precio = $_POST['precio'];

$plato = new Plato($nombre, $precio);

$dao = DaoFactory::getDao()->getDaoPlatos();

$dao->update($plato);

header('Location: ../index.php');
exit;
