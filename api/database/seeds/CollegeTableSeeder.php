<?php

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class CollegeTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('colleges')->truncate();

        DB::table('colleges')->insert([
            0 => [
                'name' => 'Faculty of Computer Science and Information Technology',
                'description' => null,
            ],

            1 => [
                'name' => 'Faculty of Medicine',
                'description' => null,
            ],

            2 => [
                'name' => 'Faculty of pharmacy',
                'description' => null,
            ],

        ]);
    }
}
