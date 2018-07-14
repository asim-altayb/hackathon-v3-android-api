<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Notifications\Notifiable;
use Illuminate\Foundation\Auth\User as Authenticatable;

class Student extends Authenticatable
{
    use Notifiable;

    protected $fillable = [
        'stdID', 'password'
    ];

    /**
     * Student has one details
     */
    public function studentDetail()
    {
        return $this->hasOne(StudentDetail::class);
    }
}
