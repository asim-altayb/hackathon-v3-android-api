<?php

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

class StudentTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('students')->truncate();

        DB::table('students')->insert([
            0 => [
                'stdID' => '2014csit001',
                'password' => Hash::make('password'),
                'group_id' => DB::table('groups')->first()->id,
            ],

            1 => [
                'stdID' => '2014csit002',
                'password' => Hash::make('password'),
                'group_id' => DB::table('groups')->first()->id,
            ],

            2 => [
                'stdID' => '2014csit003',
                'password' => Hash::make('password'),
                'group_id' => DB::table('groups')->first()->id,
            ],

            3 => [
                'stdID' => '2014csit004',
                'password' => Hash::make('password'),
                'group_id' => DB::table('groups')->first()->id,
            ]
        ]);
    }
}
