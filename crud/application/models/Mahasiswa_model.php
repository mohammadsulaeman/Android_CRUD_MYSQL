<?php

class Mahasiswa_model extends CI_Model
{
    public function edititem($dataitem, $id)
    {
        $this->db->where('id', $id);
        $this->db->update('mahasiswa', $dataitem);
        return true;
    }

    public function deleteitem($id)
    {
        $this->db->where('id', $id);
        $this->db->delete('mahasiswa');
        return true;
    }
}