<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Thread extends Model
{
    protected $fillable = [
        'student_id', 
        'replies_count',
        'visits',
        'title',
        'body',
        'media_url',
        'media_type',
        'best_reply_id',
        'locked',
        'pinned',
    ];

    public function replies(){
        return $this->hasMany(Reply::class);
    }

    public function student(){
        return $this->belongsTo(Student::class);
    }
}
