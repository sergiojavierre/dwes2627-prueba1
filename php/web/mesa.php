<?php

require_once __DIR__ . '/dao/DaoFactory.php';
include_once __DIR__ . '/entities/Plato.php';
include_once __DIR__ . '/entities/Pedido.php';

$id = $_GET['id'];
$dao = DaoFactory::getDao();

$platos = $dao->getDaoPlatos()->get();
$pedidos = $dao->getDaoMesas()->getPedidos($id);

include_once __DIR__ . '/components/nav.php';

?>


<h1>Mesa <? echo $id ?></h1>
<form action="/controller/limpiamesa.php" method="POST">
    <input type="hidden" value="<?= $id ?>" name="mesa">
    <button>Limpiar mesa</button>
</form>

<p>Platos disponibles:</p>
<?php foreach ($platos as $plato): ?>
    <form action="/controller/nuevoplato.php" method="POST">
        <input type="hidden" value="<?= $id ?>" name="mesa">
        <input type="hidden" value="<?= $plato->getNombre() ?>" name="plato">
        <button><?= $plato->getNombre() ?></button>
    </form>
<?php endforeach; ?>

<p>Pedido:</p>
<?
$total = 0;
?>
<table>
    <tr>
        <th>Plato</th>
        <th>Cantidad</th>
        <th>Precio</th>
    </tr>
    <?php foreach ($pedidos as $pedido): ?>
        <?php $total += $pedido->getCantidad() * $pedido->getPlato()->getPrecio(); ?>
        <tr>
            <td><?= $pedido->getPlato()->getNombre() ?></td>
            <td><?= $pedido->getCantidad() ?></td>
            <td><?= $pedido->getPlato()->getPrecio() ?></td>
        </tr>
    <?php endforeach; ?>
</table>

<p>Coste total: <b><?= $total ?>€</b></p>