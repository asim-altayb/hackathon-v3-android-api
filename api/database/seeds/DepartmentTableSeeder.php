<?php

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class DepartmentTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('departments')->truncate();

        DB::table('departments')->insert([
           0 => [
               'name' => 'Computer Science',
               'description' => null,
               'college_id' => DB::table('colleges')->first()->id,
           ],

            1 => [
                'name' => 'Information Technology',
                'description' => null,
                'college_id' => DB::table('colleges')->first()->id,
            ],

            2 => [
                'name' => 'Information And Communication Technology',
                'description' => null,
                'college_id' => DB::table('colleges')->first()->id,
            ],

            3 => [
                'name' => 'Information System',
                'description' => null,
                'college_id' => DB::table('colleges')->first()->id,
            ]
        ]);
    }
}
