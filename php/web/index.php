<?php

require_once __DIR__ . '/dao/DaoFactory.php';


$dao = DaoFactory::getDao();
$mesas = $dao->getDaoMesas()->get();
$platos = $dao->getDaoPlatos()->get();

include __DIR__ . '/components/nav.php';
?>

<h1>Mesas</h1>

<?php foreach ($mesas as $mesa): ?>
    <p>
        <a href="mesa.php?id=<?= $mesa->getId() ?>"><button>Mesa nº <?= $mesa->getId() ?></button></a>
    </p>
<?php endforeach; ?>

<h1>Platos</h1>

<table>
    <thead>
        <tr>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody>
        <?php foreach ($platos as $plato): ?>
            <tr>
                <td><?= $plato->getNombre() ?></td>
                <td><?= $plato->getPrecio() ?>€</td>
                <td><a href="plato.php?nombre=<?= $plato->getNombre() ?>">Modificar plato</a></td>
            </tr>
        <?php endforeach; ?>
    </tbody>
</table>