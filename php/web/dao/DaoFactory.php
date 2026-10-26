<?php

require_once __DIR__ . '/mesas/DaoMesas.php';
require_once __DIR__ . '/mesas/DaoMesasMySQL.php';
require_once __DIR__ . '/platos/DaoPlatos.php';
require_once __DIR__ . '/platos/DaoPlatosMySQL.php';

class DaoFactory
{
    private static $dao;
    private $conn;
    private $daoMesas;
    private $daoPlatos;

    public static function getDao()
    {
        if (!self::$dao) {
            self::$dao = new DaoFactory();
        }

        return self::$dao;
    }

    private function __construct()
    {
        $this->conn = new PDO(
            'mysql:host=maria;dbname=camareros;charset=utf8mb4',
            'root',
            'root',
            [PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION]
        );
    }

    public function getDaoMesas()
    {
        if (!$this->daoMesas) {
            $this->daoMesas = new DaoMesasMySQL($this->conn);
        }

        return $this->daoMesas;
    }

    public function getDaoPlatos()
    {
        if (!$this->daoPlatos) {
            $this->daoPlatos = new DaoPlatosMySQL($this->conn);
        }

        return $this->daoPlatos;
    }
}
