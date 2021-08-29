<?php

defined('BASEPATH') or exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
/** @noinspection PhpIncludeInspection */
require APPPATH . '/libraries/REST_Controller.php';

// use namespace
use Restserver\Libraries\REST_Controller;

class Mahasiswa extends REST_Controller
{
    public function __construct()
    {
        parent::__construct();
        $this->load->helper('url');
        $this->load->database();
        $this->load->model('Mahasiswa_model','mhs');
    }

    public function index_get()
    {
        $this->response("Api for crud", 200);
    }

    public function tambahdata_post()
    {
        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);

        $data_mahasiswa = array(
            'nim' => $dec_data->nim,
            'nama' => $dec_data->nama,
            'phone' => $dec_data->phone,
            'email' => $dec_data->email,
            'alamat' => $dec_data->alamat,
            'jekel' => $dec_data->jekel,
            'jurusan' => $dec_data->jurusan,
            'fakultas' => $dec_data->fakultas,
            'periode' => $dec_data->periode
        );

        $signup = $this->db->insert('mahasiswa',$data_mahasiswa);
        if ($signup) {
            $message = array(
                'code' => '200',
                'message' => 'success',
                'data' => 'Tambah Data Berhasil Di Simpan'
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '201',
                'message' => 'failed',
                'data' => ''
            );
            $this->response($message, 201);
        }
    }

    public function lihatdata_get()
    {
        $home = $this->db->get('mahasiswa')->result_array();
        $message = array(
            'code' => '200',
            'home' => $home,
            'message' => 'success'
        );
        $this->response($message, 200);
    }

    function edit_mahasiswa_post()
    {
        
        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
            $dataitem = array(
                'nim' => $dec_data->nim,
                'nama' => $dec_data->nama,
                'phone' => $dec_data->phone,
                'email' => $dec_data->email,
                'alamat' => $dec_data->alamat,
                'jekel' => $dec_data->jekel,
                'jurusan' => $dec_data->jurusan,
                'fakultas' => $dec_data->fakultas,
                'periode' => $dec_data->periode
            );
        $cekdata = $this->mhs->edititem($dataitem, $dec_data->id);
        if ($cekdata) {
            $message = array(
                'code' => '200',
                'message' => 'success'
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '200',
                'message' => 'failed'
            );
            $this->response($message, 200);
        }
    }

    function delete_mahasiswa_post()
    {
        
        $data = file_get_contents("php://input");
        $dec_data = json_decode($data);
        $cek_login = $this->mhs->deleteitem($dec_data->id);

        if ($cek_login) {
            $message = array(
                'code' => '200',
                'message' => 'success'
            );
            $this->response($message, 200);
        } else {
            $message = array(
                'code' => '200',
                'message' => 'failed'
            );
            $this->response($message, 200);
        }
    }
}
