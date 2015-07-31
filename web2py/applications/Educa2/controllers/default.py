# -*- coding: utf-8 -*-
# this file is released under public domain and you can use without limitations

#########################################################################
## This is a sample controller
## - index is the default action of any application
## - user is required for authentication and authorization
## - download is for downloading files uploaded in the db (does streaming)
#########################################################################

def index():
    """
    example action using the internationalization operator T and flash
    rendered by views/default/index.html or views/generic.html

    if you need a simple wiki simply replace the two lines below with:
    return auth.wiki()
    """
    if auth.is_logged_in():
        return redirect(URL('professor', 'index.html'))
    return dict()


def login():
    response.flash = T("Hello ")
    return dict(message=T('Welcome to web2py!'))


def create_account():
    form = auth.register()
    # print("request", request.vars)
    # create_professor(form.validate(), form)

    return dict(form=form)


def create_professor(create, form):
    if create:
        print("vars")
        print(form.vars)

        form_professor = SQLFORM(db.professor)
        form_professor.vars.nome = form.vars.first_name + form.vars.last_name
        form_professor.vars.username = form.vars.email
        print(form_professor.vars)
        # form.drop("password_two")
        user_id = db.auth_user.insert(**dict(first_name=form.vars.first_name,
                                             last_name=form.vars.last_name,
                                             email=form.vars.email,
                                             password=form.vars.password))

        id = db.professor.insert(**dict(form_professor.vars))
        response.flash = "form accepted"


def user():
    """
    exposes:
    http://..../[app]/default/user/login
    http://..../[app]/default/user/logout
    http://..../[app]/default/user/register
    http://..../[app]/default/user/profile
    http://..../[app]/default/user/retrieve_password
    http://..../[app]/default/user/change_password
    http://..../[app]/default/user/manage_users (requires membership in
    use @auth.requires_login()
        @auth.requires_membership('group name')
        @auth.requires_permission('read','table name',record_id)
    to decorate functions that need access control
    """
    return dict(form=auth())


@cache.action()
def download():
    """
    allows downloading of uploaded files
    http://..../[app]/default/download/[filename]
    """
    return response.download(request, db)


def call():
    """
    exposes services. for example:
    http://..../[app]/default/call/jsonrpc
    decorate with @services.jsonrpc the functions to expose
    supports xml, json, xmlrpc, jsonrpc, amfrpc, rss, csv
    """
    return service()


@request.restful()
# @auth.requires_login()
def api():
    response.view = 'generic.' + request.extension
    print(response.view)

    def GET(*args, **vars):
        patterns = 'auto'
        parser = db.parse_as_rest(patterns, args, vars)
        if parser.status == 200:
            return dict(content=parser.response)
        else:
            raise HTTP(parser.status, parser.error)

    def POST(table_name, **vars):
        print(vars, db[table_name])
        return db[table_name].validate_and_insert(**vars)

    def PUT(table_name, record_id, **vars):
        return db(db[table_name]._id == record_id).update(**vars)

    def DELETE(table_name, record_id):
        return db(db[table_name]._id == record_id).delete()

    return dict(GET=GET, POST=POST, PUT=PUT, DELETE=DELETE)


