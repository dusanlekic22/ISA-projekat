import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/service/user.service';
import { IUser } from '../../registration/registration/user';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css'],
})
export class UsersListComponent implements OnInit {
  users!: IUser[];

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers() {
    this.userService.getAllUser().subscribe((users) => {
      this.users = users;
    });
  }

  getAddress(user: IUser) {
    if (!user.address) return 'none';
    return (
      user.address.country +
      ', ' +
      user.address.city +
      ', ' +
      user.address.street
    );
  }

  deleteUser(id: number) {
    if (confirm('Do you want to delete this user?')) {
      this.userService.deleteUser(id).subscribe(() => this.loadUsers());
    }
  }
}
